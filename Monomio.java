package poo.polinomi;



public class Monomio implements Comparable<Monomio>
{	
	private final int COEFF;
	private final int GRADO;
	
	
	public Monomio(String s)
	{	if(s.equals(""))
		{	this.COEFF=0;
			this.GRADO=0;
		}
		else{
			boolean conX=false;
			char[]lista=s.toCharArray();
			StringBuilder coeff=new StringBuilder();
			StringBuilder grado=new StringBuilder();
			int i=0;
			for(;i<lista.length;i++)
			{	if(lista[i]=='x')
				{	conX=true;
					i+=2;	//il prx elemeno è dopo la 'x' e '^'
					break;
				}
				coeff.append(lista[i]);
			}
			for(;i<lista.length;i++)
				grado.append(lista[i]);	
			if(coeff.length()==0 || coeff.toString().equals("+"))
				coeff.append(1);	
			if(grado.length()==0 && conX)
				grado.append(1);
			if(grado.length()==0 && !conX)
				grado.append(0);



			this.GRADO=Integer.parseInt(grado.toString());
			this.COEFF=Integer.parseInt(coeff.toString());
		}
		
	}
	
	public Monomio(final int coeff,final int grado)
	{	if(grado<0)
			throw new IllegalArgumentException("grado negativo");
		this.COEFF=coeff;
		this.GRADO=grado;
	}
	
	public Monomio(Monomio m)
	{	this.COEFF=m.COEFF;
		this.GRADO=m.GRADO;
	}

	public int getCOEFF() {
		return COEFF;
	}

	public int getGRADO() {
		return GRADO;
	}
	
	public Monomio add(Monomio m)
	{	if(!this.equals(m))
			throw new RuntimeException("Monomi non simili");
		return new Monomio(this.COEFF+m.COEFF,this.GRADO);
	}//add
	
	public Monomio mul(int s)
	{	return new Monomio(COEFF*s,GRADO);
	}
	
	public Monomio mul(Monomio m)
	{	return new Monomio(COEFF*m.COEFF,GRADO+m.GRADO);
	}
	
	
	@Override
	public boolean equals(Object o)
	{	if(o==this)
			return true;
		if(!(o instanceof Monomio))
			return false;
		Monomio m=(Monomio)o;
		return m.GRADO==this.GRADO;
	}
	@Override
	public int hashCode()
	{	return GRADO;
	}
	
	public int compareTo(Monomio m)
	{	if(this.GRADO>m.GRADO)
			return -1;
		if(this.equals(m))
			return 0;
		return 1;
	}
	
	public String toString()
	{	StringBuilder sb=new StringBuilder(30);
		if(COEFF==0)
			sb.append(0);
		else 
		{	if(COEFF<1)
				sb.append('-');
			if(Math.abs(COEFF)!=1 || GRADO==0)
				sb.append(Math.abs(COEFF));
			if(COEFF!=0 && GRADO>0)
				sb.append('x');
			if(COEFF !=0 && GRADO>1)
			{	sb.append('^');
				sb.append(GRADO);
			}
		}
		return sb.toString();
	}
	
	public static void main(String...args)
	{	Monomio m=new Monomio("2x^1");
		Monomio m1=new Monomio(2,0);
		Monomio m2=new Monomio("2");
		System.out.println(m);
		System.out.println(m1);
		System.out.println(m2);
		
	}










}//Monomio
