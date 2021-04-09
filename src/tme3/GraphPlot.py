import networkx as nx
import matplotlib.pyplot as plt
import os


def plot():
    f = open("data/clusterGraph-0.4-0.02.txt", "r")
    print("reading file")
    G = nx.Graph()
    p, q  = f.readline().split()
    print(p,q)
    for e in f:
        s, t = e.split()
        G.add_edge(s,t)
    f.close()
    options = {'node_size':5, 'width':0.02}
    nx.draw(G, **options)
    plt.show()
    
plot()
