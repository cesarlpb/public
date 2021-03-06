<!-- TITLE: Datagrok for Life Sciences -->
<!-- SUBTITLE: -->

# Datagrok for Life Sciences

Datagrok is a great tool for working with tabular data of any origin. It will retrieve, analyze, 
visualize and transform traditional business data, such as sales, inventory, or marketing data as well 
as any other system (but of course faster). However, it really shines in dealing with scientific
data.

"Scientific data" is a very broad term, but there are certain attributes that quite often apply to it.
Let's take a look at them and see how Datagrok 

## Volume

Due to the nature of being measured by instruments, the volume of the data can greatly exceed
the typical "business" data.      
 
While no single computer is capable of working with datasets of unlimited size, Datagrok has 
been engineered to efficiently work with as big datasets as possible. It offers two modes:

* **Local data** - the whole dataset is loaded into the browser memory. Due to our proprietary 
  [in-memory database](../concepts/performance.md#in-memory-database), the platform can efficiently
  work with billions of rows or millions of columns. This is the preferred way of working with data,
  since it offers interactive visualizations and instant access to all underlying data points.
  Most of the datasets fall into that category.
* **Remote data** - sometimes, it is impractical or impossible to download the whole dataset, but
  you still need to work with it. In that case, Datagrok offers a number of solutions that will
  transparently translate the necessary work to be performed on the server side. Here are some
  ot them:
  * [DB Exploration](../features/db-exploration.md) to explore database schemas    
  * [DB Visual Query](../features/db-visual-query.md) to aggregate and pivot on the database side     
        
 
## Complexity

* Unstructured or semi-structured data
* Graph data
* Instrument data
* Incomplete data

## Data Discovery

Given the volume, variety, and complexity of the scientific data available to companies, 
being able to efficiently discover available datasets is   

## Rendering

Unlike business data, more often than not raw values in the scientific datasets can not be interpreted
by simply looking at them. Most common reasons for that are:
1. A value is encoded. 
   Examples: molecules encoded as SMILES
2. A value can be interpreted, but only makes sense as part of the bigger picture. 
   Example: a particular pixel of the image
3. A value makes sense only together with other attributes. 
   Examples: (LAT, LNG) points
4. A value can be interpreted only in the context of the continuous stream of data. 
   Examples: ECG values  

In order to present users with a meaningful interpretation of the dataset, the platform tries to infer
[true meaning of the raw data](../concepts/semantic-types.md), and then offers a number of visualization 
and actions that can be applied to either the dataset, or an individual point.

Examples: 
* [rendering molecules]
* [lat/lng]   
* [address]   

## Metadata

Due to the complexity of the scientific data, having the proper metadata associated with it is pivotal
in order for computers (as well as people!) to understand it. Datagrok provides a comprehensive 
[metadata management framework](../concepts/metadata.md). 
 
We also support [FAIR Principles](../concepts/fair.md) where applicable.

## Formats

Scientific data often comes in formats that are not understood by the typical business application. 
In addition to commonly used data formats such as csv, txt, xsls, xml, and json, Datagrok 
[supports](../features/importing-data.md#supported-file-types) the following ones: 

| Extension     | Description          | 
|---------------|----------------------|
| edf           | European Data Format |
| sas7bdat      | SAS                  |
| rds           | R RDS                |
| rda           | R RDA                |
| h5            | H5                   |
| nc            | NetCDF               |
| mat           | MATLAB MAT           |
| ipynb         | Jupyter Notebook     |
 

## Reproducibility

* [Jupyter notebooks](../plugins/jupyter-notebook.md)

## Integration

* LIMS systems

References
* [FAIR Principles](https://www.go-fair.org/fair-principles/)
* [Software Engineering for Machine Learning: A Case Study](https://www.microsoft.com/en-us/research/publication/software-engineering-for-machine-learning-a-case-study/)
* [Insight into complex scientific data using a graph data store](https://medium.com/blackfynn/insight-into-complex-scientific-data-using-a-graph-data-store-f2b540684c84)