import os
import sys
sys.path.append('..')
from ..lib.Functions import gcd
from ..lib.Functions import abs


class Rational:

    def __init__(self, numerator: int, denominator: int):
        if denominator == 0:
            raise Exception('Denominator cannot be zero')

        cd: int = gcd(numerator, denominator)
        if cd != 1:
            numerator /= cd
            denominator /= cd

        if (numerator > 0 and denominator < 0):
            numerator = -numerator
            denominator = -denominator

        self.numerator = numerator
        self.denominator = denominator

    def __repr__(self):
        return numerator + "/" + denominator


def Tester():
    a = Rational(2, 4)
    print(a)


Tester()
