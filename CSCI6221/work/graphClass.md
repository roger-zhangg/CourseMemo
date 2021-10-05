```go
package main

import (
	"fmt"
)


type edge struct {
	source int
	dest int
	weight int
}

type vertex struct {
	Nodes map[int]bool
	Edges map[int]map[int]*edge
}

func newVertex()vertex{
	return vertex{map[int]bool{}, map[int]map[int]*edge{}}
}

func (v vertex)addNode(nodeID int)error{
	if v.Nodes[nodeID] ==true{
		return fmt.Errorf("%v already exist",nodeID )
	}
	v.Nodes[nodeID]=true
	return nil
}

func (v vertex)addEdge(source,dest,weight int)error{
	if v.Edges[source][dest] != nil{
		return  fmt.Errorf("source:%v,dest:%v already exist",source,dest )
	}
	_,ok := v.Nodes[dest];if !ok{
		return fmt.Errorf("dest:%v,doesn't exist",dest )
	}
	_,ok = v.Nodes[source];if !ok{
		return fmt.Errorf("source:%v,doesn't exist",source )
	}
	_,ok= v.Edges[source];if !ok{
		v.Edges[source] = map[int]*edge{}
	}
	v.Edges[source][dest]= &edge{
		source: source,
		dest:   dest,
		weight: weight,
	}
	return nil
}

func (v vertex)outputAll(){
	for v,_ := range v.Nodes{
		fmt.Printf("Vertex-ID: %v\n",v)
	}
	for _,dests:=range v.Edges{
		for _,edges:=range dests{
			fmt.Printf("id e%v%v,source %v,dest %v, weight %v\n",edges.source,edges.dest,edges.source,edges.dest,edges.weight)
		}
	}
}

func main(){
	vertexTest := newVertex()
	for i:=1;i<=9;i++{
		err := vertexTest.addNode(i);if err !=nil{
			fmt.Println(err)
		}
	}
	vertexTest.addEdge(1,3,4)
	vertexTest.addEdge(1,4,5)
	vertexTest.addEdge(1,4,6)
	vertexTest.addEdge(4,3,1)
	vertexTest.outputAll()
}

```

