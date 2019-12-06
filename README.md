# Algorithms 4th Edition
### Exercises and practicing projectes
#### using java and python
#### 2019-11-05

#### updated at 2019-12-06
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


3. SUMMARY: 
3.1. For `javac` or `javac` commands, `-cp` defines the class path, followed with a string 
of paths.

3.2. For cp string in **Windows shell**, paths are separated with `;`. And cp string can be quoted or 
not quoted, if not quoted, `;` need be escaped: `\;`.

3.3. For cp string in **Linux**, paths are separated with `:`.


#### 2019-11-23
3. Add Pycairo for 2d drawing in Python:
install in python

    `pip install pycairo`

install in anaconda

    `conda install -c anaconda pycairo`

Home page is [here](https://pycairo.readthedocs.io/en/latest/)


