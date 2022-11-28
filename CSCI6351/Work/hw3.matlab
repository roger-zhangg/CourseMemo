[I,map]=imread('lake.gif'); % or imread('image', 'format'); % format can be gif, tiff, etc.
G=ind2gray(I,map);
imagesc(I); colormap(map); % for displaying the color image I
imagesc(G); colormap(gray); % for displaying the grayscale image G


dG = blockproc(G3,[N N],@(blkStruct) dct2(blkStruct.data));
[dTerm,ac1,ac2] = blockproc(dG,[N N],@(blkStruct) (getsplit(blkStruct,N)));



dG = blockproc(G,[8 8],@(blkStruct) dct2(blkStruct.data));

Ghat= blockproc(dG,[8 8],@(blkStruct) idct2(blkStruct.data));

imagesc(Ghat); colormap(gray);

two function
1: transform DC term, cd2,cd3,dc4,dc5 into vector
2: transform vector into matrix

dTerm = blockproc(dG,[8 8],@(blkStruct) (blkStruct.data(1)));
ddiag2 = blockproc(dG,[8 8],@(blkStruct) [keep(blkStruct.data,2)]);
ddiag3
ddiag4
ddiag5

qtMin = floor(min(min(ddiag1(ddiag1>0))))
qtMax = ceil(max(max(ddiag1)))
sept = [qtMin:(qtMax-qtMin)/8:qtMax-1]
codebookt = sept + (qtMax - qtMin)/16
sept = sept(2:8)
ddiag1V = reshape(ddiag1, 1, []);
[indext,quantizedt] = quantiz(ddiag1V,sept,codebookt);
ddiag1hat = reshape(quantizedt,640,832);
ddiag1hat = blockproc(ddiag1hat,[8 8],@(blkStruct) [keep(blkStruct.data,1)]);


dTmp = ddiag2+ddiag3+ddiag4+ddiag5;
qMin = floor(min(min(dTmp)))
qMax = ceil(max(max(dTmp)))
sep = [qMin:(qMax-qMin)/4:qMax-1]
codebook = sep + (qMax - qMin)/8
sep = sep(2:4)
dTmpV = reshape(dTmp, 1, []);
[index,quantized] = quantiz(dTmpV,sep,codebook);
guhat = reshape(quantized,640,832);
guhat = blockproc(guhat,[8 8],@(blkStruct) [keep(blkStruct.data,0)]);


dGhat = guhat + ddiag1hat;

Ghat= blockproc(dGhat,[8 8],@(blkStruct) idct2(blkStruct.data));
imagesc(Ghat); colormap(gray);
snr(double(G),double(G)-Ghat)



function m1 = keep(in1, in2)
  m1 = zeros(size(in1));
  switch in2
        case 2
        	m1(2,1) = in1(2,1);
        	m1(1,2) = in1(1,2);
        case 3
        	m1(3,1) = in1(3,1);
        	m1(2,2) = in1(2,2);	
        	m1(1,3) = in1(1,3);	
        case 4
        	m1(4,1) = in1(4,1);
        	m1(3,2) = in1(3,2);	
        	m1(2,3) = in1(2,3);	
            m1(1,4) = in1(1,4);	
        case 5
        	m1(5,1) = in1(5,1);
        	m1(4,2) = in1(4,2);	
        	m1(3,3) = in1(3,3);	
            m1(2,4) = in1(2,4);	
        	m1(1,5) = in1(1,5);
        case 
  end
end