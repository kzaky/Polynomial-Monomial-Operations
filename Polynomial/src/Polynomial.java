import java.util.ArrayList;

/** 
 * This class represents polynomials as an array list of Monomial objects. Each mMnomial object has two fields, coefficient
 *(double) and degree (double). These fields can be accessed and mutated via methods provided in the Monomial class.
 */

public class Polynomial {
    
  private ArrayList<Monomial> polynomial;
  
  
  // Constructor method
  
  
  Polynomial(ArrayList<Monomial> polynomial){
    this.polynomial=polynomial;
  }
  
  
  /* Insertion Sort method: sorts the Polynomial created by appending input poly to this in order of increasing degree, 
  returns the resulting Polynomial. It is used in sum method*/
  
  
  private Polynomial insertionSort(Polynomial poly){ 
    Polynomial sortedPoly = new Polynomial(this.polynomial);
    for (int i = 0; i < poly.polynomial.size(); i++){
      sortedPoly.polynomial.add(poly.polynomial.get(i));
    }   
    for (int j = 1; j < sortedPoly.polynomial.size(); j++){
      Monomial mono = sortedPoly.polynomial.get(j);
      int k = j - 1; // index used to reference elements before mono in this
      while (k >= 0 && sortedPoly.polynomial.get(k).getDegree() > mono.getDegree()){ // shifts elements > mono up 1 index
        sortedPoly.polynomial.set(k+1, sortedPoly.polynomial.get(k));
        k--;
      }
      sortedPoly.polynomial.set(k+1, mono); 
    }
    return sortedPoly;
  }
  
  
  /*Sum method: takes a polynomial as input, adds it to the polynomial which the method is called on, and returns the sum of
  the polynomials*/


  public Polynomial sum(Polynomial poly){
    Polynomial sortedPoly = this.insertionSort(poly);
    Polynomial sumPoly = new Polynomial(new ArrayList<Monomial>());
    Monomial sumMono;
    int degree1,degree2,sumDegree;
    double sumCoeff = 0;
    
    for (int i = 0; i < sortedPoly.polynomial.size(); i++){
      if (i < sortedPoly.polynomial.size() - 1){ // when i != last element, compare i to i + 1
        degree1 = sortedPoly.polynomial.get(i).getDegree();
        degree2 = sortedPoly.polynomial.get(i+1).getDegree();
      } else { // when i = last element, add last Monomial to sumPoly
        degree1 = sortedPoly.polynomial.get(i).getDegree();
        degree2 = degree1 + 1; // makes degree1 != degree2 so if block will execute
      }
      sumCoeff += sortedPoly.polynomial.get(i).getCoeff();
      if (degree1 != degree2){
        sumDegree = degree1;
        sumMono = new Monomial(sumCoeff, sumDegree);
        sumPoly.polynomial.add(sumMono);
        sumCoeff = 0;
      }
    }
    return sumPoly;
  }
    
  
  //Degree method: returns max degree of polynomial. It is used in evaluate method
  

  private int degree(){
  
    //initializes the maximum degree of the monomials in the polynomial
    int maxDegree = 0;
  
    //checks each monomial for the largest degree
    for (Monomial monomial : this.polynomial){
      if (maxDegree<=monomial.getDegree()){
      maxDegree= monomial.getDegree();
      }
    }

  return maxDegree;
}
     
  
  //Evaluate method: Takes a real value x and evaluates the polynomial which the method is called on at x using Horner's algorithm.

  
  public double evaluate(double x){
      
    //initializes the value of the evaluated polynomial
    double value = 0;
      
    //iterates from the value of the degree of the polynomial, ie x^3 would iterate from 3-->0
    for (double monomialDegree=this.degree();monomialDegree >= 0;monomialDegree--){
          
      //a holder for the value of the coefficient based on monomial degree
      double actualCoefficient = 0;
          
          
      /*iterates through polynomial array and determines what monomials can 
       * be factored together, and which have a coefficient of 0.
       */
      
      for (Monomial monomial : this.polynomial){
              
         //checks to see if the monomialDegree exists in the given Polynomial
         if(monomial.getDegree() == monomialDegree){
                  
           //if it does exist, all the coefficients are added together
           actualCoefficient+= monomial.getCoeff();
         }
              
         else{
           //otherwise they are 0
           actualCoefficient +=0;
         }
      }
          
      //Using Horner's method the polynomial is evaluated at a given x
      value = value*x + actualCoefficient;
      
    }
      
      
    return value;
  }
    
    
  /*Multiply method: Takes a polynomial as input, multiplies it with the polynomial which the method is called on, 
  and returns the product of the polynomials*/
  

  public Polynomial multiply(Polynomial poly){
  Polynomial productPoly = new Polynomial(new ArrayList<Monomial>()); 
  Monomial productMono;

  for (int i = 0; i < this.polynomial.size(); i++){
      for (int j = 0; j < poly.polynomial.size(); j++){
          productMono  = new Monomial(this.polynomial.get(i).getCoeff()*poly.polynomial.get(j).getCoeff()
                                     ,this.polynomial.get(i).getDegree()+poly.polynomial.get(j).getDegree());
          productPoly.polynomial.add(productMono);          
      }
  }
  return productPoly;
}
 
  
  //Differentiate method: differentiates the polynomial which the method is called on, and returns the resulting polynomial.
  
  
  public Polynomial differentiate(){  
    Polynomial diffPoly = new Polynomial(this.polynomial);
    int deg;//Initiates a variable to store the degree
    double coeff; //Initiates a variable to store the coefficient
    int i=0;
    
    while(i<diffPoly.polynomial.size()){//Loops through the arraylist 
        
      //Sets variables for the degree and coefficient for the current monomial
      deg = diffPoly.polynomial.get(i).getDegree();  
      coeff = diffPoly.polynomial.get(i).getCoeff();
        
      //Following differeniation rules, if the degree is 0, then we remove the monomial
      if (deg==0){
        diffPoly.polynomial.remove(diffPoly.polynomial.get(i));              
      }
      /*If the degree does not equal 0, then the coefficient 
       * is multiplied by the degree, an the degree is decreased by one
       */
      else{
        diffPoly.polynomial.get(i).setCoeff(deg*coeff);
        diffPoly.polynomial.get(i).setDegree(deg-1);
        i++;
      }
    }
    return diffPoly;
  }
 
  
  //Integrate method: Takes real values a and b, integrates the polynomial which the method is called on over [a,b], and returns the value.
 
  
  public double integrate(double a, double b){
    for(Monomial mono : this.polynomial){
      mono.setDegree(mono.getDegree() + 1);
      mono.setCoeff(mono.getCoeff()/mono.getDegree());
    }
    
    return this.evaluate(b) - this.evaluate(a);
        
  }
    
   
  //Epsilon method: Calculates and returns the machine epsilon. It is used in the square root method.
  

  private float epsilon(){
    float E = 1.0f;
    do{
      E /= 2.0f;
    } while ((float)(1.0 + E/2.0) != 1.0);
    return E;
  } 

  
  /*Square Root Method: Takes a real value x, uses the evaluate method to evaluate the polynomial which the method is called 
  on at x, and returns the value of the square root of that value using Newton-Raphason's method.*/
  
  
  public double squareRoot(double x0){

  double N = this.evaluate(x0); //Evaluate the polynomial at a point x0
  double x = N; //Initial guess is the polynomial evaluated at x0
  double epsilon = epsilon(); //Compute the degree of accuracy

  //Continually apply Newton-Raphson's method until maximum accuracy is attained
  while (x - N/x > epsilon*x) {
      x = (N/x + x) / 2.0;
  }
  
  return x;
}

  
  // print method: prints out Polynomial, used in test method
  
  
  private void print(){
  double coeff;
  int degree;
  for (int i = 0; i < this.polynomial.size(); i++){
    coeff = this.polynomial.get(i).getCoeff();
    degree = this.polynomial.get(i).getDegree();
    if (i < this.polynomial.size()-1){
      System.out.print(coeff + "x^" + degree + " + ");
    } else {
      System.out.print(coeff + "x^" + degree);
    }
  }
  System.out.println();
}
  
  
  // Test method: tests other public methods 
  
  
  public void test(){
    
    // sum method test
    System.out.println("Sum method test: ");
    Polynomial poly1 = new Polynomial(new ArrayList<Monomial>());
    poly1.polynomial.add(new Monomial (1,1));
    poly1.polynomial.add(new Monomial (2,2));
    poly1.polynomial.add(new Monomial (3,3));
    poly1.polynomial.add(new Monomial (4,4));
    System.out.print("p1(x) = ");
    poly1.print();
    Polynomial poly2 = new Polynomial(new ArrayList<Monomial>());
    poly2.polynomial.add(new Monomial (4,1));
    poly2.polynomial.add(new Monomial (3,2));
    poly2.polynomial.add(new Monomial (2,3));
    poly2.polynomial.add(new Monomial (1,4));
    System.out.print("p2(x) = ");
    poly2.print();
    Polynomial sumPoly = poly1.sum(poly2);
    System.out.print("p1(x) + p2(x) = ");
    sumPoly.print();
    System.out.println();

    
    // evaluate method test
    System.out.println("Evaluate method test:");
    Polynomial evalPoly = new Polynomial(new ArrayList<Monomial>());
    evalPoly.polynomial.add(new Monomial(4,32));
    evalPoly.polynomial.add(new Monomial(5,1));
    evalPoly.polynomial.add(new Monomial(2,200));
    evalPoly.polynomial.add(new Monomial(3,9));     
    System.out.print("p3(x) = ");
    evalPoly.print();
    System.out.println("p3(20) = " + evalPoly.evaluate(20));
    System.out.println();
    
    
    // multiply method test
    System.out.println("Multiply method test:");    
    Polynomial poly4 = new Polynomial(new ArrayList<Monomial>());
    poly4.polynomial.add(new Monomial (1,0));
    poly4.polynomial.add(new Monomial (4,1));
    poly4.polynomial.add(new Monomial (2,2));
    poly4.polynomial.add(new Monomial (3,3));
    System.out.print("p4(x) = ");
    poly4.print();   
    Polynomial poly5 = new Polynomial(new ArrayList<Monomial>());
    poly5.polynomial.add(new Monomial (4,4));
    poly5.polynomial.add(new Monomial (3,3));
    poly5.polynomial.add(new Monomial (1,3));
    poly5.polynomial.add(new Monomial (2,1));
    System.out.print("p5(x) = ");
    poly5.print();
    Polynomial productPoly = poly4.multiply(poly5);
    System.out.print("p4(x) * p5(x) = ");
    productPoly.print();
    System.out.println();
    
    
    // differentiate method test
    System.out.println("Differentiate method test:");
    Polynomial poly6 = new Polynomial(new ArrayList<Monomial>());
    poly6.polynomial.add(new Monomial(4,32));
    poly6.polynomial.add(new Monomial(5,1));
    poly6.polynomial.add(new Monomial(3,0));
    poly6.polynomial.add(new Monomial(3,7));
    System.out.print("p6(x) = ");
    poly6.print();
    Polynomial diffPoly = poly6.differentiate();
    System.out.print("d/dx p6(x) = ");
    diffPoly.print();
    System.out.println();
    
    // integrate method test
    System.out.println("Integrate method test: ");
    Polynomial poly7 = new Polynomial(new ArrayList <Monomial>());
    poly7.polynomial.add(new Monomial(1,0));
    poly7.polynomial.add(new Monomial(4,2));
    poly7.polynomial.add(new Monomial(5,3));
    poly7.polynomial.add(new Monomial(42.3,7));
    System.out.print("p7(x) = ");
    poly7.print();
    System.out.println("integral of p7(x) from -2.6 to 13.4 = " + poly7.integrate(-2.6,13.4));
    System.out.println();
    
    //square root method test     
    System.out.println("Square root method test:");
    Polynomial sqrt = new Polynomial(new ArrayList<Monomial>());
    sqrt.polynomial.add(new Monomial(4,3));
    sqrt.polynomial.add(new Monomial(5,1));
    sqrt.polynomial.add(new Monomial(2,4));
    sqrt.polynomial.add(new Monomial(3,2));     
    System.out.print("p(x) = ");
    sqrt.print();
    System.out.println("p(20) = " + sqrt.evaluate(2));
    System.out.println("The square root is: " + sqrt.squareRoot(2));

    }
  
  public static void main(String[] args) {
    Polynomial poly = new Polynomial(new ArrayList<Monomial>());
    poly.test();
    

  }
  
}
