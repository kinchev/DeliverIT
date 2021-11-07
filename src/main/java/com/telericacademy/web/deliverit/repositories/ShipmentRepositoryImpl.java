package com.telericacademy.web.deliverit.repositories;

import com.telericacademy.web.deliverit.exceptions.EntityNotFoundException;
import com.telericacademy.web.deliverit.models.Shipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipmentRepositoryImpl implements ShipmentRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ShipmentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


//    @Override
//    public List<Shipment> getAll(Optional<Integer> warehouseId) {
//        try (Session session = sessionFactory.openSession()) {
//            if (warehouseId.isPresent()) {
//                Query<Shipment> query = session.createQuery("from Shipment where originWarehouse.id =:id or destinationWarehouse.id=:id", Shipment.class);
//                query.setParameter("id", warehouseId.get());
//                return query.list();
//            }
//            Query<Shipment> query = session.createQuery("from Shipment ", Shipment.class);
//            return query.list();
//        }
//    }

    @Override
    public List<Shipment> getAll() {
        try (Session session = sessionFactory.openSession()) {

            Query<Shipment> query = session.createQuery("from Shipment", Shipment.class);
            return query.list();
        }
    }


    @Override
    public List<Shipment> filterByWarehouse(int warehouseId) {
        try (Session session = sessionFactory.openSession()) {


                Query<Shipment> query = session.createQuery("from Shipment where originWarehouseId.id = :id or destinationWarehouseId.id = :id", Shipment.class);
                query.setParameter("id", warehouseId);
                return query.list();
            }
        }

//    @Override
//    public List<Shipment> filterByCustomer(int customerId) {
//
//          try  (Session session=sessionFactory.openSession()){
//              Query<Shipment> query=session.createQuery("from User where user_id =:id and ")
//
//        }
//
//
//
//    }


    @Override
    public Shipment getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Shipment shipment = session.get(Shipment.class, id);
            if (shipment == null) {
                throw new EntityNotFoundException("Shipment", id);
            }
            return shipment;
        }
    }
//
//    @Override
//    public List<Shipment> filter(Optional<Integer> originWarehouseId, Optional<Integer> destinationWarehouseId) {
//        try (Session session = sessionFactory.openSession()) {
//            var queryString = new StringBuilder("from Shipment ");
//            var filters = new ArrayList<String>();
//            var params = new HashMap<String, Object>();
//
//            originWarehouseId.ifPresent(originWarehouse -> {
//                filters.add(" originWarehouseId.id = :originWarehouseId");
//                params.put("originWarehouseId", originWarehouseId.get());
//
//            });
//            destinationWarehouseId.ifPresent(destinationWarehouse -> {
//                filters.add(" destinationWarehouseId.id = :destinationWarehouseId ");
//                params.put("destinationWarehouseId", destinationWarehouseId.get());
//
//            });
//
//            if (!filters.isEmpty()) {
//                queryString.append("where ")
//                        .append(String.join(" and ", filters));
//            }
////            queryString.append("where ").append(String.join(" and ", filters));
//            Query<Shipment> query = session.createQuery(queryString.toString(), Shipment.class);
//            query.setProperties(params);
//
//
//            return query.list();
//        }
//    }


    @Override
    public void create(Shipment shipment) {
        try (Session session = sessionFactory.openSession()) {
            session.save(shipment);
        }
    }

    @Override
    public void update(Shipment shipment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(shipment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Shipment shipmentToDelete = getById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(shipmentToDelete);
            session.getTransaction().commit();
        }


    }


//    private Integer getNumberValueIfPresent(String str) {
//        try {
//            return Integer.parseInt(str);
//        } catch (NumberFormatException e) {
//            return -1;
//        }
//    }
}
