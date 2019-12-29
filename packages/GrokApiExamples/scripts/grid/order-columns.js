// Order columns. Columns not mentioned in the parameters will be positioned after the specified ones.

let view = grok.addTableView(grok.testData('demog', 5000));
view.grid.columns.setOrder(['age', 'sex', 'race']);