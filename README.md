# PdfExtraction
./svm-scale -l 0 -u 1 -s /home/zeta/projects/spminer/PdfExtraction/src/main/resources/data/data.scale /home/zeta/projects/spminer/PdfExtraction/src/main/resources/data/data_features.txt > data_scale.txt
python subset.py data_scale.txt 632 data_train.txt data_test.txt
