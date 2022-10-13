s = "the dog in the fog"
empty_arr = []
tmp_s = ""
for i in range(0,len(s)):
	empty_arr.append(s[i:]+s[0:i])


empty_arr.sort()
print(empty_arr)
for i in range(0,len(s)):
	tmp_s = tmp_s + empty_arr[i][len(s)-1]
print (tmp_s)

# tmp_b = ["c","b","b","a","a","a","b","c","b"]
# tmp_b.sort()
# y = ["c","b","b","a","a","a","b","c","b"]
# for i in range(0,len(s)):
# 	for i in range(0,len(s)):
# 		tmp_b[i]=y[i]+tmp_b[i]
# 	tmp_b.sort()
# 	print(tmp_b)


n2 = 15;
result = [];
for j =1:n2
	X5X = dct(x);
	X5X(17-j:16) = 0;
	x5x = idct(X5X);
	result(j) = immse(x5x,x);
end
plot(1:n2,result);





H=0.25*hadamard(16);
result = [];
X5c = x* H;
for j =1:n2
	X5cX = X5c;
	for i=1:j
	  	[val(i),idx] = min(X5cX);
	  	X5cX(idx) = 10000;
	end
	for i=1:j
	  	[val(i),idx] = max(X5cX);
	  	X5cX(idx) = 0;
	end
	x5cx = X5cX * H';
	result(j) = immse(x5cx,x);
end
plot(1:n2,result);








	