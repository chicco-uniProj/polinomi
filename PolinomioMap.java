package poo.polinomi;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class PolinomioMap extends PolinomioAstratto
{	private Map<Integer,Monomio> monomi=new TreeMap<>();
	
	@Override
	public int size()
	{	return monomi.size();
	}
	
	@Override
	public Iterator<Monomio> iterator()
	{	return monomi.values().iterator();
	}
	
	@Override
	public PolinomioMap crea()
	{	return new PolinomioMap();
	}
	
	@Override
	public void add(Monomio m) 
	{	if(m.getCOEFF()==0)
			return;
		Iterator<Monomio>it=monomi.values().iterator();
		while(it.hasNext())
		{	Monomio c=it.next();
			Monomio ret=null;
			if(m.equals(c) && m.getCOEFF()-c.getCOEFF()==0)
			{	it.remove();
				return;
			}
			else if(m.equals(c))
			{	ret=c.add(m);
				monomi.put(ret.getGRADO(), ret);
				return;
			}
		}
		monomi.put(m.getGRADO(), m);
	}
	

}
