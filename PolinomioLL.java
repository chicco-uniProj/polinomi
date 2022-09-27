package poo.polinomi;

import java.util.*;

public class PolinomioLL extends PolinomioAstratto
{	private LinkedList<Monomio>lista=new LinkedList<>();
	
	@Override
	public int size()
	{	return lista.size();//piu efficente di quello nell'interface
	}
	
	@Override
	public PolinomioLL crea()  
	{	return new PolinomioLL();//covarianza del tipo di ritorno
	}
	
	@Override
	public Iterator<Monomio>iterator()
	{	return lista.iterator();
	}

	public void add(Monomio m)
	{	if(m.getCOEFF()==0)
			return;
		ListIterator<Monomio>lit=lista.listIterator();
		boolean flag=false;
		while(lit.hasNext() && !flag)
		{	Monomio mc=lit.next();
			if(mc.equals(m))
			{	Monomio nm=mc.add(m);
				if(nm.getCOEFF()==0)
					lit.remove();
				else
					lit.set(nm);
				flag=true;
			}
			else if(mc.compareTo(m)>0)
			{	lit.previous();
				lit.add(m);
				flag=true;
			}
			
		}//while
		if(!flag)
			lit.add(m);
		
			
	}//add

	public static void main(String...args)
	{	Polinomio p=new PolinomioLL();
		p.add(new Monomio("+3x^2"));
		p.add(new Monomio("4x^2"));
		p.add(new Monomio("3x^3"));
		p.add(new Monomio("+8x^32"));
		
		p.add(new Monomio("8"));
		System.out.println(p);
		
		
		
		
		
	}
	
	
}
