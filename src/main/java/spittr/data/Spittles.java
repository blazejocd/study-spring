package spittr.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import spittr.Spittle;

@Component
public class Spittles implements SpittleRepository 
{
	@Override
	public List<Spittle> findSpittles(long max, int count) {
		List<Spittle> list = new ArrayList<>();
		for(int i = 0; i<count; i++) {
			Spittle spittle = new Spittle(
					(long)Integer.valueOf(i), "Message " +i, new Date(),
					8484.484, 68484.8484);
			list.add(spittle);
		}
		return list;
	}

	@Override
	public Spittle findOne(long id) {
		return new Spittle(id, "Hejka, hejka! ;)", new Date(), 345.33,243.24442);
	}

}