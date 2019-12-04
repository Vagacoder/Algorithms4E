from .python.lib.Functions import *

class Rational:

    def __init__ (self, numerator: int, denominator : int):
        if denominator == 0:
            raise Exception('Denominator cannot be zero')
        
        gcd: int = Functions.gcd(numerator, denominator)
        if gcd != 1:
            numerator /= gcd
            denominator /= gcd
        
        if (numerator > 0 and denominator < 0):
            numerator = -numerator
            denominator = -denominator

        self.numerator = numerator
        self.denominator = denominator

    def __repr__ (self):
        return numerator + "/" + denominator


def Tester():
    a = Rational(2, 4)
    print(a)


Tester()
