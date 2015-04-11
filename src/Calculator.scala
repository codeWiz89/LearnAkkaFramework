import java.math.BigInteger

class Calculator {
  
  def calculateFactorial(n: Int): BigInteger = {
    
    var result: BigInteger = BigInteger.valueOf(1)
    var i = 0
    
    for(i <- 1 to n) {      
      result = result.multiply(BigInteger.valueOf(i))      
    }        
//    println("result: " + result)
    result
  }
}