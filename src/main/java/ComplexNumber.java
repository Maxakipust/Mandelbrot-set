public class ComplexNumber {
    private double real;
    private double imaginary;
    public ComplexNumber(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public void setReal(double real) {
        this.real = real;
    }
    public void setImaginary(double imaginary){
        this.imaginary = imaginary;
    }
    public double getReal(){
        return real;
    }
    public double getImaginary(){
        return  imaginary;
    }
    public ComplexNumber add(ComplexNumber num){
        return new ComplexNumber(this.real+num.real,this.imaginary+num.imaginary);
    }
    public ComplexNumber subtract(ComplexNumber num){
        return new ComplexNumber(this.real-num.real, this.imaginary-num.imaginary);
    }
    public double magnitude(){
        return Math.sqrt(Math.pow(this.real,2)+Math.pow(this.imaginary,2));
    }
    public ComplexNumber square(){
        return new ComplexNumber(Math.pow(this.real,2)-Math.pow(this.imaginary,2),2*this.real*this.imaginary);
    }

    @Override
    public String toString() {
        String r = real+"";
        String i = imaginary>=0?"+"+imaginary:imaginary+"";
        return r+i+"i";
    }
}
