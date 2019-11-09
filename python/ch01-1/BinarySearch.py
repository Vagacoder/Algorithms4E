
class BinarySearch:

    def rank(self, key: int, a: [int]) -> int:
        lo: int = 0
        hi: int = len(a) - 1
        while (lo <= hi):
            mid: int = lo + (hi-lo)//2
            if (key < a[mid]):
                hi = mid - 1
            elif (key > a[mid]):
                lo = mid + 1
            else:
                return mid
        return -1

def main():
    BS = BinarySearch()
    print(BS.rank(4, [1, 2, 3 , 4, 5]))

main()