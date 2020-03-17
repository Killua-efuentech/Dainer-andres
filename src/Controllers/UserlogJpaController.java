/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Ingventa;
import Entity.Userlog;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dainer
 */
public class UserlogJpaController implements Serializable {

    public UserlogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Userlog userlog) {
        if (userlog.getIngventaList() == null) {
            userlog.setIngventaList(new ArrayList<Ingventa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingventa> attachedIngventaList = new ArrayList<Ingventa>();
            for (Ingventa ingventaListIngventaToAttach : userlog.getIngventaList()) {
                ingventaListIngventaToAttach = em.getReference(ingventaListIngventaToAttach.getClass(), ingventaListIngventaToAttach.getId());
                attachedIngventaList.add(ingventaListIngventaToAttach);
            }
            userlog.setIngventaList(attachedIngventaList);
            em.persist(userlog);
            for (Ingventa ingventaListIngventa : userlog.getIngventaList()) {
                Userlog oldIdlogOfIngventaListIngventa = ingventaListIngventa.getIdlog();
                ingventaListIngventa.setIdlog(userlog);
                ingventaListIngventa = em.merge(ingventaListIngventa);
                if (oldIdlogOfIngventaListIngventa != null) {
                    oldIdlogOfIngventaListIngventa.getIngventaList().remove(ingventaListIngventa);
                    oldIdlogOfIngventaListIngventa = em.merge(oldIdlogOfIngventaListIngventa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Userlog userlog) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Userlog persistentUserlog = em.find(Userlog.class, userlog.getId());
            List<Ingventa> ingventaListOld = persistentUserlog.getIngventaList();
            List<Ingventa> ingventaListNew = userlog.getIngventaList();
            List<String> illegalOrphanMessages = null;
            for (Ingventa ingventaListOldIngventa : ingventaListOld) {
                if (!ingventaListNew.contains(ingventaListOldIngventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingventa " + ingventaListOldIngventa + " since its idlog field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ingventa> attachedIngventaListNew = new ArrayList<Ingventa>();
            for (Ingventa ingventaListNewIngventaToAttach : ingventaListNew) {
                ingventaListNewIngventaToAttach = em.getReference(ingventaListNewIngventaToAttach.getClass(), ingventaListNewIngventaToAttach.getId());
                attachedIngventaListNew.add(ingventaListNewIngventaToAttach);
            }
            ingventaListNew = attachedIngventaListNew;
            userlog.setIngventaList(ingventaListNew);
            userlog = em.merge(userlog);
            for (Ingventa ingventaListNewIngventa : ingventaListNew) {
                if (!ingventaListOld.contains(ingventaListNewIngventa)) {
                    Userlog oldIdlogOfIngventaListNewIngventa = ingventaListNewIngventa.getIdlog();
                    ingventaListNewIngventa.setIdlog(userlog);
                    ingventaListNewIngventa = em.merge(ingventaListNewIngventa);
                    if (oldIdlogOfIngventaListNewIngventa != null && !oldIdlogOfIngventaListNewIngventa.equals(userlog)) {
                        oldIdlogOfIngventaListNewIngventa.getIngventaList().remove(ingventaListNewIngventa);
                        oldIdlogOfIngventaListNewIngventa = em.merge(oldIdlogOfIngventaListNewIngventa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userlog.getId();
                if (findUserlog(id) == null) {
                    throw new NonexistentEntityException("The userlog with id " + id + " no longer exists.");
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
            Userlog userlog;
            try {
                userlog = em.getReference(Userlog.class, id);
                userlog.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userlog with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingventa> ingventaListOrphanCheck = userlog.getIngventaList();
            for (Ingventa ingventaListOrphanCheckIngventa : ingventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Userlog (" + userlog + ") cannot be destroyed since the Ingventa " + ingventaListOrphanCheckIngventa + " in its ingventaList field has a non-nullable idlog field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(userlog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Userlog> findUserlogEntities() {
        return findUserlogEntities(true, -1, -1);
    }

    public List<Userlog> findUserlogEntities(int maxResults, int firstResult) {
        return findUserlogEntities(false, maxResults, firstResult);
    }

    private List<Userlog> findUserlogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Userlog.class));
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

    public Userlog findUserlog(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Userlog.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserlogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Userlog> rt = cq.from(Userlog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
