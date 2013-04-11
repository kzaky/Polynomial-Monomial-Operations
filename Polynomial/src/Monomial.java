
public class Monomial {
	
	private double coeff;
	private int degree;
	
	Monomial(double coeff, int degree){
		this.coeff=coeff;
		this.degree=degree;
	}
	
	public double getCoeff(){
		return this.coeff;
	}
	
	public int getDegree(){
		return this.degree;
	}
	
	public void setCoeff(double coeff){
		this.coeff=coeff;
	}
	
	public void setDegree(int degree){
		this.degree=degree;
	}
	

}
