
# functions

# 1. Math Functions
# 1.1. Square root

def sqrt(x:float) -> float :
    if (x < 0):
        return None
    
    err: float = 1e-15
    result: float = x
    while(Math.abs(result - x/result) > err * result):
        result = (x / result + result) / 2.0
    
    return result

