package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.Parcel;
import com.telericacademy.web.deliverit.repositories.contracts.ParcelRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class ParcelRepositoryImpl implements ParcelRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ParcelRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Parcel> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Parcel> query = session.createQuery("from Parcel", Parcel.class);
            return query.list();
        }
    }


    public List<Parcel> filter(Optional<Double> minWeight, Optional<String> lastName, Optional<Integer> originWarehouseId,
                               Optional<Integer> destinationWarehouseId, Optional<Integer> categoryId, Optional<String> sort) {

        try (Session session = sessionFactory.openSession()) {
            var queryString = new StringBuilder("from Parcel ");
            var filters = new ArrayList<String>();
            var params = new HashMap<String, Object>();

            minWeight.ifPresent(value -> {
                filters.add("weight >= :minWeight");
                params.put("minWeight", value);
            });

            lastName.ifPresent(value -> {
                filters.add("user.lastName like :lastName");
                params.put("lastName", "%" + value + "%");
            });

            originWarehouseId.ifPresent(value -> {
                filters.add("originWarehouse.id = :warehouseId");
                params.put("warehouseId", value);
            });

            destinationWarehouseId.ifPresent(value -> {
                filters.add("destinationWarehouse.id = :warehouseId");
                params.put("warehouseId", value);
            });

            categoryId.ifPresent(value -> {
                filters.add("category.id = :categoryId");
                params.put("categoryId", value);
            });

            if (!filters.isEmpty()) {
                queryString.append("where ")
                        .append(String.join(" and ", filters));
            }

            sort.ifPresent(value -> {
                queryString.append(generateSortingString(value));
            });
            Query<Parcel> query = session.createQuery(queryString.toString(), Parcel.class);
            query.setProperties(params);
            return query.list();
        }
    }

    @Override
    public Parcel getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Parcel parcel = session.get(Parcel.class, id);
            if (parcel == null) {
                throw new EntityNotFoundException("Parcel", id);
            }
            return parcel;
        }
    }

    @Override
    public void create(Parcel parcel) {
        try (Session session = sessionFactory.openSession()) {
            session.save(parcel);
        }
    }

    @Override
    public void update(Parcel parcel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(parcel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Parcel parcelToDelete = getById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(parcelToDelete);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Parcel> sortBy(Optional<Double> weight, Optional<LocalDate> arrivalDate) {
        return null;
    }

    private String generateSortingString(String value) {
        StringBuilder result = new StringBuilder(" order by ");
        var params = value.toLowerCase().split("_");

        switch (params[0]) {
            case "weight":
                result.append("weight ");
                break;
            case "name":
                result.append("lastName ");
                break;
            case "warehouse":
                result.append("warehouse.id ");
                break;
            case "category":
                result.append("category.name ");
                break;
            default:
                return "";
        }

        if (params.length > 1 && params[1].equals("desc")) {
            result.append("desc");
        }
        return result.toString();

    }
}


//    private List<Parcel> filterByWeight(List<Parcel> result, double weight) {
//        if (String.valueOf(weight).isBlank()){
//            return result;
//        }
//        return result.stream()
//                .filter1(parcel -> parcel.getWeight() >= weight)
//                .collect(Collectors.toList());
//    }
//
//    private List<Parcel> filterByCustomer(List<Parcel> result, String customer) {
//        if (customer == null || customer.isBlank()) {
//            return result;
//        }
//        return result.stream()
//                .filter1(parcel -> parcel.getCustomer().getLastName().equalsIgnoreCase(customer))
//                .collect(Collectors.toList());
//    }
//
//    private List<Parcel> filterByWarehouse(List<Parcel> result, String warehouse) {
//        if (warehouse == null || warehouse.isBlank()) {
//            return result;
//        }
//        return result.stream()
//                .filter1(parcel -> parcel.getWarehouse().getCityName().equalsIgnoreCase(warehouse))
//                .collect(Collectors.toList());
//    }
//
//    private List<Parcel> filterByCategory(List<Parcel> result, String category) {
//        if (category == null || category.isBlank()) {
//            return result;
//        }
//        return result.stream()
//                .filter1(parcel -> parcel.getCategory().equalsIgnoreCase(category))
//                .collect(Collectors.toList());
//    }
//
//    private List<Parcel> sortBy(List<Parcel> result, String sortBy) {
//        if (sortBy == null || sortBy.isBlank()) {
//            return result;
//        }
//        switch (sortBy.toLowerCase()) {
//            case "weight" :
//                result.sort(Comparator.comparing(Parcel::getWeight));
//                break;
//            case "customer":
//                result.sort(Comparator.comparing(parcel -> parcel.getCustomer().getLastName()));
//                break;
//            case "warehouse" :
//                result.sort(Comparator.comparing(parcel -> parcel.getWarehouse().getCityName()));
//                break;
//            case "category" :
//                result.sort(Comparator.comparing(Parcel::getCategory));
//                break;
//
//        }
//        return result;
//    }
//
//
//
//    private List<Parcel> sortOrder(List<Parcel> result, String order) {
//        if (order == null || order.isBlank()) {
//            return result;
//        }
//        if (order.equalsIgnoreCase("desc")) {
//            Collections.reverse(result);
//        }
//        return result;
//    }
//
//}
