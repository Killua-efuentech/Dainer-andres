/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Entity.Ingventa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Userlog;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dainer
 */
public class IngventaJpaController implements Serializable {

    public IngventaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingventa ingventa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Userlog idlog = ingventa.getIdlog();
            if (idlog != null) {
                idlog = em.getReference(idlog.getClass(), idlog.getId());
                ingventa.setIdlog(idlog);
            }
            em.persist(ingventa);
            if (idlog != null) {
                idlog.getIngventaList().add(ingventa);
                idlog = em.merge(idlog);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingventa ingventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingventa persistentIngventa = em.find(Ingventa.class, ingventa.getId());
            Userlog idlogOld = persistentIngventa.getIdlog();
            Userlog idlogNew = ingventa.getIdlog();
            if (idlogNew != null) {
                idlogNew = em.getReference(idlogNew.getClass(), idlogNew.getId());
                ingventa.setIdlog(idlogNew);
            }
            ingventa = em.merge(ingventa);
            if (idlogOld != null && !idlogOld.equals(idlogNew)) {
                idlogOld.getIngventaList().remove(ingventa);
                idlogOld = em.merge(idlogOld);
            }
            if (idlogNew != null && !idlogNew.equals(idlogOld)) {
                idlogNew.getIngventaList().add(ingventa);
                idlogNew = em.merge(idlogNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingventa.getId();
                if (findIngventa(id) == null) {
                    throw new NonexistentEntityException("The ingventa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingventa ingventa;
            try {
                ingventa = em.getReference(Ingventa.class, id);
                ingventa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingventa with id " + id + " no longer exists.", enfe);
            }
            Userlog idlog = ingventa.getIdlog();
            if (idlog != null) {
                idlog.getIngventaList().remove(ingventa);
                idlog = em.merge(idlog);
            }
            em.remove(ingventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingventa> findIngventaEntities() {
        return findIngventaEntities(true, -1, -1);
    }

    public List<Ingventa> findIngventaEntities(int maxResults, int firstResult) {
        return findIngventaEntities(false, maxResults, firstResult);
    }

    private List<Ingventa> findIngventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingventa.class));
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

    public Ingventa findIngventa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngventaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingventa> rt = cq.from(Ingventa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Ingventa> obtenerVentas() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Ingventa c WHERE c.fecha!=''")
                    .setHint("javax.persistence.cache.storeMode", "REFRESH")
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
}
