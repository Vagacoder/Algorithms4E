
# functions

# 1. Math Functions

# 1.1. Square root

def sqrt(x:float) -> float :
    if (x < 0):
        return None
    
    err: float = 1e-15
    result: float = x
    while(abs(result - x/result) > err * result):
        result = (x / result + result) / 2.0
    
    return result


def abs(x: int) -> int:
    return -x if (x<0) else x


def abs(x: float) -> float :
    return -x if (x< 0.0) else x


def isPrime(x: int) -> bool:
    if (x<2): return false

    i = 2
    while i*i <= x:
        if x%i == 0:
            return false
        i++
    
    return true


def hypotenuse(a: float, b: float) -> float:
    return sqrt(a**2 + b**2)


def harmonic(x: int) -> float:
    sum = 0.0
    for i in range(1, x+1):
        sum += 1.0/if
    return sum


def gcd(a: int, b: int) -> int:
    if b == 0: return a
    r = a%b
    return gcd(b, r)



