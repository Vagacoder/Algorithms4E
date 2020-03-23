package javasrc.ch02_5;

/*
* 2.5.10 Create a data type Version that represents a software version number, 
such as 115.1.1, 115.10.1, 115.10.2. Implement the Comparable interface so that 
115.1.1 is less than 115.10.1, and so forth.

*/

import lib.*;

public class Version implements Comparable<Version>{

    private int major;
    private int minor;
    private int patch;

    public Version(){

    }

    public Version(int major, int minor, int patch){
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }


    public String getVersion(){
        return "" + this.major + "." + this.minor + "." + this.patch;
    }

    @Override
    public int compareTo(Version that) {
        if(this.major != that.major){return this.major - that.major;}
        if(this.minor != that.minor) {return this.minor - that.minor;}
        if(this.patch != that.patch) {return this.patch - that.patch;}
        return 0;
    }



    public static void main(String[] args){
        
    }


}