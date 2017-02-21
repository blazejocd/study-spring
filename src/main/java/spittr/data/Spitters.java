package spittr.data;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spittr.Spitter;
import spittr.exceptions.SpitterAlreadyExists;

@Repository
@Transactional
public class Spitters implements SpitterRepository 
{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Spitter save(Spitter spitter) 
	{
		try {
			em.persist(spitter);
			return spitter;
		} catch (EntityExistsException e) {
			throw new SpitterAlreadyExists();
		}
	}

	@Override
	public Spitter findByUsername(String name) 
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Spitter> criteria = cb.createQuery(Spitter.class);
		Root<Spitter> root = criteria.from(Spitter.class);
		criteria.select(root);
		criteria.where(cb.equal(root.<Set<String>>get("username"), name));
		Spitter result = em.createQuery(criteria).getSingleResult();
		
		return result;
	}

	@Override
	public Spitter findSpitterById(Long id) 
	{
		return em.find(Spitter.class, id);
	}

	@Override
	public Spitter[] findAllSpitters() 
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = cb.createTupleQuery();
		Root<Spitter> root = criteria.from(Spitter.class);
		
		Path<String> firstNamePath = root.get("firstName");
		Path<String> lastNamePath = root.get("lastName");
		Path<String> emailPath = root.get("email");
		
		criteria.multiselect(firstNamePath, lastNamePath, emailPath);
		List<Tuple> results = em.createQuery(criteria).getResultList();
		
		return this.iterateOverTuple(results, firstNamePath, lastNamePath, emailPath);
	}
	
	private Spitter[] iterateOverTuple(List<Tuple> tuples, Path<String>...paths)
	{
		Spitter[] spitters = new Spitter[tuples.size()];
		for (int i=0; i < tuples.size(); i++)
		{
			Tuple tuple = tuples.get(i);
			Spitter spitter = new Spitter();
			spitter.setFirstName((String) tuple.get(paths[0]));
			spitter.setLastName((String) tuple.get(paths[1]));
			spitter.setEmail((String) tuple.get(paths[2]));
			spitters[i] = spitter;
		}
		return spitters;
	}
}