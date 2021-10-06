// this code is achieved with golang 1.17
// interface is used to implement Abstract Data Type

package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

const typeString = "string"
const typeInt = "int"

type customQueue struct {
	Elements  interface{}
	QueueType string
}

func newCustomQueue(queueType string)*customQueue{
	switch queueType{
	case typeString:
		return &customQueue{[]string{},typeString}
	case typeInt:
		return &customQueue{[]int{},typeInt}
	default:
		log.Fatalf("method %v not implemented yet",queueType)
		return nil
	}
}

func (q *customQueue)push(element string){
	switch q.QueueType {
	case typeInt:
		value,err := strconv.Atoi(element);if err != nil{
			fmt.Printf("error converting input %v to int, %v\n",value,err)
		}else{
			//add value to last
			q.Elements = append(q.Elements.([]int),value)
		}
	case typeString:
		//add value to last
		q.Elements = append(q.Elements.([]string),element)
	default:
		log.Fatalf("method %v not implemented yet",q.QueueType)
	}
}

func (q *customQueue)pop(){
	switch q.QueueType {
	case typeInt:
		if len(q.Elements.([]int)) > 0{
			fmt.Printf("poping an element %v\n",q.Elements.([]int)[0])
			//delete first element
			q.Elements = q.Elements.([]int)[1:]
		}else{
			fmt.Printf("There is no element in the queue\n")
		}
	case typeString:
		if len(q.Elements.([]string)) > 0{
			fmt.Printf("poping an element %v\n",q.Elements.([]string)[0])
			//delete first element
			q.Elements = q.Elements.([]string)[1:]
		}else{
			fmt.Printf("There is no element in the queue\n")
		}
	default:
		log.Fatalf("method %v not implemented yet",q.QueueType)
	}
}

func (q *customQueue)count()int {
	switch q.QueueType {
	case typeInt:
		return len(q.Elements.([]int))
	case typeString:
		return len(q.Elements.([]string))
	default:
		log.Fatalf("method %v not implemented yet",q.QueueType)
		return 0
	}
}

func requestUserInput(prompt string,caseSensitive bool)string{
	fmt.Printf("%v\n",prompt)
	stdinReader := bufio.NewReader(os.Stdin)
	var text string
	//ignore empty enter here
	for{
		text, _ = stdinReader.ReadString('\n')
		text = strings.Replace(text, "\n", "", -1)
		if len(text)!=0{
			break
		}
	}
	if caseSensitive{
		return text
	}
	// not caseSensitive, just return lower text for easy matching
	return strings.ToLower(text)
}


func main(){
	queueTypeStr := requestUserInput("Please select queue type(String/Integer)",false)
	if strings.HasPrefix("string",queueTypeStr){
		queueTypeStr = typeString
	}else if strings.HasPrefix("integer",queueTypeStr){
		queueTypeStr = typeInt
	}else{
		log.Fatalf("%v detected, expecting string/integer",queueTypeStr)
	}
	queue := newCustomQueue(queueTypeStr)
	for {
		switch requestUserInput("======please select======\na.push\nb.pop\nc.count\nd.end",false){
		// a or push all eligible
		case "a","push":
			queue.push(requestUserInput(fmt.Sprintf("please enter the element in (%v) format",queueTypeStr),true))
		case "b","pop":
			queue.pop()
		case "c","count":
			fmt.Printf("%v Elements detected\n",queue.count())
		case "d","end":
			os.Exit(0)
		default:
			fmt.Printf("unexpected input detected\n")
		}
	}
}