# Algorithms 4th Edition
### Exercises and practicing projectes
#### using java and python

#### 201-11-05, 2019-12-06
1. How to complie .java file in command line:
1.1. in **Windows shell**, 
if the classpath is not defined, we have a source file, 
*BinarySearch.java* in the folder **./javasrc/ch01_1/**, use:

    `javac -cp ./\;./lib/algs4.jar javasrc/ch01_1/BinarySearch.java`

    or

    `javac -cp "./;./lib/algs4.jar" javasrc/ch01_1/BinarySearch.java`

1.2. in **Linux bash**
Same situation as 1.1.

    `javac -cp "./:./lib/algs4.jar" javasrc/ch01_1/BinarySearch.java`



2. How to run compiled .class file in command line:
2.1. in **Windows shell**, if the classpath is not defined, we have a compiled file,
*BinarySearch.class* in the folder **./javasrc/ch01_1/**, and 2 data files 
*./data/tinyW.txt* and *./data/tinyT.txt* for arguments, use:

    `java -cp ./\;./lib/algs4.jar javasrc/ch01_1/BinarySearch data/tinyW.txt < data/tinyT.txt`

    or

    `java -cp "./;./lib/algs4.jar" javasrc/ch01_1/BinarySearch data/tinyW.txt < data/tinyT.txt`

2.2. in **Linux bash**
Same situation in 2.1.

    `java -cp "./:./lib/algs4.jar" javasrc/ch01_1/BinarySearch data/tinyW.txt < data/tinyT.txt`


3. SUMMARY of compiling and running using command line: 
3.1. For `javac` or `javac` commands, `-cp` defines the class path, followed with a string 
of paths.

3.2. For cp string in **Windows shell**, paths are separated with `;`. And cp string can be quoted or 
not quoted, if not quoted, `;` need be escaped: `\;`.

3.3. For cp string in **Linux**, paths are separated with `:`.


#### 2019-11-23
4. Add Pycairo for 2d drawing in Python:
install in python

    `pip install pycairo`

install in anaconda

    `conda install -c anaconda pycairo`

Home page is [here](https://pycairo.readthedocs.io/en/latest/)


#### 2019-12-07
5. How to input EOF "End Of File" in command line.
In **Windows shell**, it is `Ctrl+Z`
In **Linux bash**, its is `Ctrl+D`

6. How to create generic array in java
There are *Strong Typing* and *Weak Typing*, we talk *Weak Typing* here

    public class FixedCapacityStack<T> {

        private T[] a;
        private int N;

        public FixedCapacityStack(int capacity) {
            @SuppressWarnings("unchecked")
            T[] a = (T[]) new Object[capacity];
            this.a = a;
        }
    }

6.1. You need annotation: @SuppressWarnings("unchecked"),
6.2. Create an array of object (new Object[capacity]) first,
6.3. Then, cast it to generic type,
6.4. casted generic array can not be directly assigned to instance variable, use a template variable to transfer.

#### 2019-12-09
6. Immutable integer range
Java's implementation of `valueOf()` retrieves a cached values if the integer is `between -128 and 127`,
while Java constructs new objects for each integer outside this range. 

