#name: t-test
#description: Welch's t-test
#help-url: https://en.wikipedia.org/wiki/Welch%27s_t-test
#language: r
#tags: demo
#sample: TSLA.csv
#input: dataframe data [Input data table]
#input: column x {type:numerical} [X axis column name]
#input: column y {type:numerical} [Y axis column name]
#output: double pValue [P-value of t-statistics]

require(stats)

ttest = t.test(data[[x]], data[[y]])
pValue = ttest$p.value
