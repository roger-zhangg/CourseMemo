from sklearn.metrics import mean_squared_error


def ml_adj(n, d ,r, x):
    level_sum = [0 for i in range(0,n)]
    level_count = [0 for i in range(0,n)]
    new_r = []
    new_d = []
    for num in x:
        counter = 0
        for dl in d:
            if num >= dl:
                counter += 1
                continue
            level_sum[counter-1] += num
            level_count[counter-1] += 1
            break

    for i in range(0,n):
        if level_count[i] == 0:
            new_r.append(r[i])
        else:
            new_r.append(level_sum[i]/level_count[i])
    new_d.append(d[0])
    for i in range(1,n):
        new_d.append((new_r[i]+new_r[i-1])/2)
    new_d.append(d[n])
    return new_r,new_d

def printarr(a):
    print(["{0:0.5f}".format(i) for i in a])

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    x = [0, 0.52, 0.55, 0.68, 0.91, 0.94, 0.97, 1.03, 1.04, 1.2, 1.3, 1.35, 1.4, 1.47, 1.6, 1.7,
         1.85, 1.95, 1.99, 2.2, 2.28, 2.45, 2.48, 2.56, 2.63, 2.67, 2.85, 3, 3.39, 3.57, 3.86, 3.99]
    for n in(5,6,8):
        print(n)
        d = [4-(n-i)*4/n for i in range(0,n+1)]
        r = [(d[i]+d[i-1])/2 for i in range(1,n+1)]
        new_d = d
        new_r = r
        printarr(d)
        printarr(r)
        # for max lloyd
        for i in range(0,200):
            new_r,new_d = ml_adj(n,new_d,new_r,x)
        # for uniform
        new_r1 = r
        ned_d1 = d
        # for semi uniform
        new_r2, _ = ml_adj(n, new_d, new_r, x)
        new_d2 = d
        
        printarr(new_d)
        printarr(new_r)
        new_index = []
        for num in x:
            counter = 0
            for dl in d:
                if num >= dl:
                    counter += 1
                    continue
                new_index.append(counter-1)
                break
        new_x = [new_r[new_index[i]] for i in range (0,len(x))]
        print(new_index)
        printarr(new_x)
        print("{0:0.5f}".format(mean_squared_error(x,new_x)))



