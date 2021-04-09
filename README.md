# CPA. Conception Pratique d'Algorithmes

### Requirements
- java >= 8.0 
- ant
- Jung package\
    For Girvan–Newman algorithm \
    http://jung.sourceforge.net/download.html

- networkx python library to plot

### Installation
#### Linux (deb/ubuntu)
`sudo apt install ant` 


#### Windows (7/8/10)
install ant :\
 https://mkyong.com/ant/how-to-install-apache-ant-on-windows/

` directory : ${PATH}/cpa `\
`ant clean` \
`ant build` 

#### Environment variable
To use properly the build file you should add \
`${amazon}` ,`${lj} `and `${orkut}`\
To test amazon, live-journal and orkut benchmarks 
#### Running examples 
`ant Counter`\
`ant GraphGeneratorTest`\
`ant Clustering`
