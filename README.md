# Algorithms 4th Edition
### Exercises and practicing projectes
#### using java and python
#### 2019-11-05

1. How to complie .java file in command line:
in **windows bash**, if the classpath is not defined, we have a source file, 
*BinarySearch.java* in the folder **./javasrc/ch01_1/**, use:

    `javac -cp ./\;./lib/algs4.jar javasrc/ch01_1/BinarySearch.java`


2. How to run compiled .class file in command line:
in **windows bash**, if the classpath is not defined, we have a compiled file,
*BinarySearch.java* in the folder **./javasrc/ch01_1/**, and 2 data files 
*./data/tinyW.txt* and *./data/tinyT.txt* for arguments, use:

    `java -cp ./\;./lib/algs4.jar javasrc/ch01_1/BinarySearch data/tinyW.txt < data/tinyT.txt`

#### 2019-11-23
3. Add Pycairo for 2d drawing in Python:
install in python

    `pip install pycairo`

install in anaconda

    `conda install -c anaconda pycairo`

Home page is [here](https://pycairo.readthedocs.io/en/latest/)


