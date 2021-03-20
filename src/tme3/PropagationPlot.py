import networkx as nx
import matplotlib.pyplot as plt



def plot():
    
    g = open("data/labelGraph-0.5-0.5.txt", "r")
    print("reading file")
    labels = {}
    for e in g:
        n, l = e.split()
        if l in labels:
            labels[l].append(int(n))
        else:
            labels[l] = [int(n)]
    print(labels)
    g.close()
    edges = []
    f = open("data/clusterGraph-0.5-0.5.txt", "r")
    p, q  = f.readline().split()
    print(p,q)
    for e in f:
        s, t = e.split()
        edges.append((int(s),int(t)))
    f.close()
    colors = ["r","b", "m", "c" ]
    G = nx.Graph()
    
    
    G.add_edges_from(edges)
    colormap = ["g"]*400
    for l in labels:
        c=colors.pop()
        print(labels[l],c)
        for node in labels[l]:

            colormap[node] = c
    
    options = {'node_size':5, 'width':0.01, 'node_color' : colormap, 'nodelist':range(400)}
    
   
    nx.draw(G, **options)
    plt.show()
    
plot()
