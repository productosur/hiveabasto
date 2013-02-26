/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.productosur.hive.dbcontrollers;

import com.productosur.hive.dbcontrollers.exceptions.IllegalOrphanException;
import com.productosur.hive.dbcontrollers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.productosur.hive.entities.EmployeesRoles;
import com.productosur.hive.entities.Users;
import java.util.ArrayList;
import java.util.Collection;
import com.productosur.hive.entities.DailyRoutes;
import com.productosur.hive.entities.Employees;
import com.productosur.hive.entities.TraceRoute;
import com.productosur.hive.entities.Orders;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Productosur
 */
public class EmployeesJpaController implements Serializable {

    public EmployeesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employees employees) {
        if (employees.getUsersCollection() == null) {
            employees.setUsersCollection(new ArrayList<Users>());
        }
        if (employees.getDailyRoutesCollection() == null) {
            employees.setDailyRoutesCollection(new ArrayList<DailyRoutes>());
        }
        if (employees.getTraceRouteCollection() == null) {
            employees.setTraceRouteCollection(new ArrayList<TraceRoute>());
        }
        if (employees.getOrdersCollection() == null) {
            employees.setOrdersCollection(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmployeesRoles employeeroleId = employees.getEmployeeroleId();
            if (employeeroleId != null) {
                employeeroleId = em.getReference(employeeroleId.getClass(), employeeroleId.getId());
                employees.setEmployeeroleId(employeeroleId);
            }
            Collection<Users> attachedUsersCollection = new ArrayList<Users>();
            for (Users usersCollectionUsersToAttach : employees.getUsersCollection()) {
                usersCollectionUsersToAttach = em.getReference(usersCollectionUsersToAttach.getClass(), usersCollectionUsersToAttach.getId());
                attachedUsersCollection.add(usersCollectionUsersToAttach);
            }
            employees.setUsersCollection(attachedUsersCollection);
            Collection<DailyRoutes> attachedDailyRoutesCollection = new ArrayList<DailyRoutes>();
            for (DailyRoutes dailyRoutesCollectionDailyRoutesToAttach : employees.getDailyRoutesCollection()) {
                dailyRoutesCollectionDailyRoutesToAttach = em.getReference(dailyRoutesCollectionDailyRoutesToAttach.getClass(), dailyRoutesCollectionDailyRoutesToAttach.getId());
                attachedDailyRoutesCollection.add(dailyRoutesCollectionDailyRoutesToAttach);
            }
            employees.setDailyRoutesCollection(attachedDailyRoutesCollection);
            Collection<TraceRoute> attachedTraceRouteCollection = new ArrayList<TraceRoute>();
            for (TraceRoute traceRouteCollectionTraceRouteToAttach : employees.getTraceRouteCollection()) {
                traceRouteCollectionTraceRouteToAttach = em.getReference(traceRouteCollectionTraceRouteToAttach.getClass(), traceRouteCollectionTraceRouteToAttach.getId());
                attachedTraceRouteCollection.add(traceRouteCollectionTraceRouteToAttach);
            }
            employees.setTraceRouteCollection(attachedTraceRouteCollection);
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : employees.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getId());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            employees.setOrdersCollection(attachedOrdersCollection);
            em.persist(employees);
            if (employeeroleId != null) {
                employeeroleId.getEmployeesCollection().add(employees);
                employeeroleId = em.merge(employeeroleId);
            }
            for (Users usersCollectionUsers : employees.getUsersCollection()) {
                Employees oldEmployeeIdOfUsersCollectionUsers = usersCollectionUsers.getEmployeeId();
                usersCollectionUsers.setEmployeeId(employees);
                usersCollectionUsers = em.merge(usersCollectionUsers);
                if (oldEmployeeIdOfUsersCollectionUsers != null) {
                    oldEmployeeIdOfUsersCollectionUsers.getUsersCollection().remove(usersCollectionUsers);
                    oldEmployeeIdOfUsersCollectionUsers = em.merge(oldEmployeeIdOfUsersCollectionUsers);
                }
            }
            for (DailyRoutes dailyRoutesCollectionDailyRoutes : employees.getDailyRoutesCollection()) {
                Employees oldEmployeeIdOfDailyRoutesCollectionDailyRoutes = dailyRoutesCollectionDailyRoutes.getEmployeeId();
                dailyRoutesCollectionDailyRoutes.setEmployeeId(employees);
                dailyRoutesCollectionDailyRoutes = em.merge(dailyRoutesCollectionDailyRoutes);
                if (oldEmployeeIdOfDailyRoutesCollectionDailyRoutes != null) {
                    oldEmployeeIdOfDailyRoutesCollectionDailyRoutes.getDailyRoutesCollection().remove(dailyRoutesCollectionDailyRoutes);
                    oldEmployeeIdOfDailyRoutesCollectionDailyRoutes = em.merge(oldEmployeeIdOfDailyRoutesCollectionDailyRoutes);
                }
            }
            for (TraceRoute traceRouteCollectionTraceRoute : employees.getTraceRouteCollection()) {
                Employees oldEmployeeIdOfTraceRouteCollectionTraceRoute = traceRouteCollectionTraceRoute.getEmployeeId();
                traceRouteCollectionTraceRoute.setEmployeeId(employees);
                traceRouteCollectionTraceRoute = em.merge(traceRouteCollectionTraceRoute);
                if (oldEmployeeIdOfTraceRouteCollectionTraceRoute != null) {
                    oldEmployeeIdOfTraceRouteCollectionTraceRoute.getTraceRouteCollection().remove(traceRouteCollectionTraceRoute);
                    oldEmployeeIdOfTraceRouteCollectionTraceRoute = em.merge(oldEmployeeIdOfTraceRouteCollectionTraceRoute);
                }
            }
            for (Orders ordersCollectionOrders : employees.getOrdersCollection()) {
                Employees oldEmployeeIdOfOrdersCollectionOrders = ordersCollectionOrders.getEmployeeId();
                ordersCollectionOrders.setEmployeeId(employees);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldEmployeeIdOfOrdersCollectionOrders != null) {
                    oldEmployeeIdOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldEmployeeIdOfOrdersCollectionOrders = em.merge(oldEmployeeIdOfOrdersCollectionOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employees employees) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employees persistentEmployees = em.find(Employees.class, employees.getId());
            EmployeesRoles employeeroleIdOld = persistentEmployees.getEmployeeroleId();
            EmployeesRoles employeeroleIdNew = employees.getEmployeeroleId();
            Collection<Users> usersCollectionOld = persistentEmployees.getUsersCollection();
            Collection<Users> usersCollectionNew = employees.getUsersCollection();
            Collection<DailyRoutes> dailyRoutesCollectionOld = persistentEmployees.getDailyRoutesCollection();
            Collection<DailyRoutes> dailyRoutesCollectionNew = employees.getDailyRoutesCollection();
            Collection<TraceRoute> traceRouteCollectionOld = persistentEmployees.getTraceRouteCollection();
            Collection<TraceRoute> traceRouteCollectionNew = employees.getTraceRouteCollection();
            Collection<Orders> ordersCollectionOld = persistentEmployees.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = employees.getOrdersCollection();
            List<String> illegalOrphanMessages = null;
            for (Users usersCollectionOldUsers : usersCollectionOld) {
                if (!usersCollectionNew.contains(usersCollectionOldUsers)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Users " + usersCollectionOldUsers + " since its employeeId field is not nullable.");
                }
            }
            for (DailyRoutes dailyRoutesCollectionOldDailyRoutes : dailyRoutesCollectionOld) {
                if (!dailyRoutesCollectionNew.contains(dailyRoutesCollectionOldDailyRoutes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DailyRoutes " + dailyRoutesCollectionOldDailyRoutes + " since its employeeId field is not nullable.");
                }
            }
            for (TraceRoute traceRouteCollectionOldTraceRoute : traceRouteCollectionOld) {
                if (!traceRouteCollectionNew.contains(traceRouteCollectionOldTraceRoute)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TraceRoute " + traceRouteCollectionOldTraceRoute + " since its employeeId field is not nullable.");
                }
            }
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersCollectionOldOrders + " since its employeeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (employeeroleIdNew != null) {
                employeeroleIdNew = em.getReference(employeeroleIdNew.getClass(), employeeroleIdNew.getId());
                employees.setEmployeeroleId(employeeroleIdNew);
            }
            Collection<Users> attachedUsersCollectionNew = new ArrayList<Users>();
            for (Users usersCollectionNewUsersToAttach : usersCollectionNew) {
                usersCollectionNewUsersToAttach = em.getReference(usersCollectionNewUsersToAttach.getClass(), usersCollectionNewUsersToAttach.getId());
                attachedUsersCollectionNew.add(usersCollectionNewUsersToAttach);
            }
            usersCollectionNew = attachedUsersCollectionNew;
            employees.setUsersCollection(usersCollectionNew);
            Collection<DailyRoutes> attachedDailyRoutesCollectionNew = new ArrayList<DailyRoutes>();
            for (DailyRoutes dailyRoutesCollectionNewDailyRoutesToAttach : dailyRoutesCollectionNew) {
                dailyRoutesCollectionNewDailyRoutesToAttach = em.getReference(dailyRoutesCollectionNewDailyRoutesToAttach.getClass(), dailyRoutesCollectionNewDailyRoutesToAttach.getId());
                attachedDailyRoutesCollectionNew.add(dailyRoutesCollectionNewDailyRoutesToAttach);
            }
            dailyRoutesCollectionNew = attachedDailyRoutesCollectionNew;
            employees.setDailyRoutesCollection(dailyRoutesCollectionNew);
            Collection<TraceRoute> attachedTraceRouteCollectionNew = new ArrayList<TraceRoute>();
            for (TraceRoute traceRouteCollectionNewTraceRouteToAttach : traceRouteCollectionNew) {
                traceRouteCollectionNewTraceRouteToAttach = em.getReference(traceRouteCollectionNewTraceRouteToAttach.getClass(), traceRouteCollectionNewTraceRouteToAttach.getId());
                attachedTraceRouteCollectionNew.add(traceRouteCollectionNewTraceRouteToAttach);
            }
            traceRouteCollectionNew = attachedTraceRouteCollectionNew;
            employees.setTraceRouteCollection(traceRouteCollectionNew);
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getId());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            employees.setOrdersCollection(ordersCollectionNew);
            employees = em.merge(employees);
            if (employeeroleIdOld != null && !employeeroleIdOld.equals(employeeroleIdNew)) {
                employeeroleIdOld.getEmployeesCollection().remove(employees);
                employeeroleIdOld = em.merge(employeeroleIdOld);
            }
            if (employeeroleIdNew != null && !employeeroleIdNew.equals(employeeroleIdOld)) {
                employeeroleIdNew.getEmployeesCollection().add(employees);
                employeeroleIdNew = em.merge(employeeroleIdNew);
            }
            for (Users usersCollectionNewUsers : usersCollectionNew) {
                if (!usersCollectionOld.contains(usersCollectionNewUsers)) {
                    Employees oldEmployeeIdOfUsersCollectionNewUsers = usersCollectionNewUsers.getEmployeeId();
                    usersCollectionNewUsers.setEmployeeId(employees);
                    usersCollectionNewUsers = em.merge(usersCollectionNewUsers);
                    if (oldEmployeeIdOfUsersCollectionNewUsers != null && !oldEmployeeIdOfUsersCollectionNewUsers.equals(employees)) {
                        oldEmployeeIdOfUsersCollectionNewUsers.getUsersCollection().remove(usersCollectionNewUsers);
                        oldEmployeeIdOfUsersCollectionNewUsers = em.merge(oldEmployeeIdOfUsersCollectionNewUsers);
                    }
                }
            }
            for (DailyRoutes dailyRoutesCollectionNewDailyRoutes : dailyRoutesCollectionNew) {
                if (!dailyRoutesCollectionOld.contains(dailyRoutesCollectionNewDailyRoutes)) {
                    Employees oldEmployeeIdOfDailyRoutesCollectionNewDailyRoutes = dailyRoutesCollectionNewDailyRoutes.getEmployeeId();
                    dailyRoutesCollectionNewDailyRoutes.setEmployeeId(employees);
                    dailyRoutesCollectionNewDailyRoutes = em.merge(dailyRoutesCollectionNewDailyRoutes);
                    if (oldEmployeeIdOfDailyRoutesCollectionNewDailyRoutes != null && !oldEmployeeIdOfDailyRoutesCollectionNewDailyRoutes.equals(employees)) {
                        oldEmployeeIdOfDailyRoutesCollectionNewDailyRoutes.getDailyRoutesCollection().remove(dailyRoutesCollectionNewDailyRoutes);
                        oldEmployeeIdOfDailyRoutesCollectionNewDailyRoutes = em.merge(oldEmployeeIdOfDailyRoutesCollectionNewDailyRoutes);
                    }
                }
            }
            for (TraceRoute traceRouteCollectionNewTraceRoute : traceRouteCollectionNew) {
                if (!traceRouteCollectionOld.contains(traceRouteCollectionNewTraceRoute)) {
                    Employees oldEmployeeIdOfTraceRouteCollectionNewTraceRoute = traceRouteCollectionNewTraceRoute.getEmployeeId();
                    traceRouteCollectionNewTraceRoute.setEmployeeId(employees);
                    traceRouteCollectionNewTraceRoute = em.merge(traceRouteCollectionNewTraceRoute);
                    if (oldEmployeeIdOfTraceRouteCollectionNewTraceRoute != null && !oldEmployeeIdOfTraceRouteCollectionNewTraceRoute.equals(employees)) {
                        oldEmployeeIdOfTraceRouteCollectionNewTraceRoute.getTraceRouteCollection().remove(traceRouteCollectionNewTraceRoute);
                        oldEmployeeIdOfTraceRouteCollectionNewTraceRoute = em.merge(oldEmployeeIdOfTraceRouteCollectionNewTraceRoute);
                    }
                }
            }
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    Employees oldEmployeeIdOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getEmployeeId();
                    ordersCollectionNewOrders.setEmployeeId(employees);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldEmployeeIdOfOrdersCollectionNewOrders != null && !oldEmployeeIdOfOrdersCollectionNewOrders.equals(employees)) {
                        oldEmployeeIdOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldEmployeeIdOfOrdersCollectionNewOrders = em.merge(oldEmployeeIdOfOrdersCollectionNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employees.getId();
                if (findEmployees(id) == null) {
                    throw new NonexistentEntityException("The employees with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employees employees;
            try {
                employees = em.getReference(Employees.class, id);
                employees.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employees with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Users> usersCollectionOrphanCheck = employees.getUsersCollection();
            for (Users usersCollectionOrphanCheckUsers : usersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Users " + usersCollectionOrphanCheckUsers + " in its usersCollection field has a non-nullable employeeId field.");
            }
            Collection<DailyRoutes> dailyRoutesCollectionOrphanCheck = employees.getDailyRoutesCollection();
            for (DailyRoutes dailyRoutesCollectionOrphanCheckDailyRoutes : dailyRoutesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the DailyRoutes " + dailyRoutesCollectionOrphanCheckDailyRoutes + " in its dailyRoutesCollection field has a non-nullable employeeId field.");
            }
            Collection<TraceRoute> traceRouteCollectionOrphanCheck = employees.getTraceRouteCollection();
            for (TraceRoute traceRouteCollectionOrphanCheckTraceRoute : traceRouteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the TraceRoute " + traceRouteCollectionOrphanCheckTraceRoute + " in its traceRouteCollection field has a non-nullable employeeId field.");
            }
            Collection<Orders> ordersCollectionOrphanCheck = employees.getOrdersCollection();
            for (Orders ordersCollectionOrphanCheckOrders : ordersCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Orders " + ordersCollectionOrphanCheckOrders + " in its ordersCollection field has a non-nullable employeeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EmployeesRoles employeeroleId = employees.getEmployeeroleId();
            if (employeeroleId != null) {
                employeeroleId.getEmployeesCollection().remove(employees);
                employeeroleId = em.merge(employeeroleId);
            }
            em.remove(employees);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employees> findEmployeesEntities() {
        return findEmployeesEntities(true, -1, -1);
    }

    public List<Employees> findEmployeesEntities(int maxResults, int firstResult) {
        return findEmployeesEntities(false, maxResults, firstResult);
    }

    private List<Employees> findEmployeesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employees.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Employees findEmployees(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employees.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employees> rt = cq.from(Employees.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
