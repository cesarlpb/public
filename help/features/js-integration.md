<!-- TITLE: JavaScript Integration -->
<!-- SUBTITLE: -->

# JavaScript Integration

Datagrok exposes [JavaScript API](https://dev.datagrok.ai/js/grok_api.js) 
that lets you control the platform from the client side.
It is written in an object-oriented style and is easy to use. 

Access the platform via the global 'gr' variable. The API is self-explanatory, let's take a look at the following example:
```javascript
let demog = grok.testData('demog', 5000);       // create test table
let view = grok.addTableView(demog);            // add it as a view
let hist = view.addViewer('histogram');       // add histogram to that view
hist.options({'valueColumnName': 'weight'});  // customize the histogram
``` 

## Executing scripts

Stand-alone scripts can be executed by running the script from the JavaScript Editor (#{cmd(OpenJavaScriptEditor)}).

## JavaScript plugins

This is still work in progress. 

## Examples

The following examples (also accessible from the JavaScript Editor's toolbox) demonstrate different
capabilities of the JavaScript integration. 

#### Table Manipulation

```javascript
// Table manipulations

demog = grok.testData('demog', 5000);
demog.cols.remove('sex');
foo = demog.cols.addNew('foo', 'int');
demog.rows.removeAt(1, 3);
demog.rows.insertAt(2, 2);
demog.rows.addNew(['Spiderman', 'studyX', 'NYC', 32, 'Spider', 'Net', new Date(2020), 180, 80, 666]);
demog.rows.addNew().subj = 'Iron Man';

// alternative ways of setting values
foo.set(1, 777);
demog.set('age', 1, 44);
//demog.currentRow.age = 33;

// bit set (same applies to filter)
demog.selection.invert();
demog.selection.set(5, false);
demog.selection.findNext(0, false);
```

#### Async Functions   

```javascript
// An example of using Dart's future as JavaScript's promises

grok.openTable("8352e030-5970-11e9-b8a0-4f392518d7b3")
  .then(t => grok.balloon.info(t.name));
```

#### Registering JavaScript functions

This examples shows how to register a function that becomes a first-class
citizen in the platform (i.e., it can be used from console, gets registered
in help, gets an optional audit trail associated with the invocations, etc)

The code below registers two functions, "jsConcat" and "jsWidget". To test
jsConcat, enter "jsConcat(42, 33)" in the [Console](../features/console.md). 
To test jsWidget, create a new Dashboard, and click on "Widget" under "Widgets".

For asynchronous JS functions, set flag "isAsync" to "true", see "jsSuggestCountryName" example.

```javascript

grok.functions.register({
    signature: 'String jsConcat(int foo, int bar)',
    run: (foo, bar) => `${foo}_${bar}`});

grok.functions.register({
    signature: 'Widget jsWidget()',
    tags: 'Widgets',
    run: function() {
        let e = document.createElement('DIV');
        function update() {
            let date = new Date();
            e.innerText = date.toTimeString();
        }
        window.setTimeout(update, 1000);

        return new JsWidget(e);
    }});

grok.functions.register({
  signature: 'List<String> jsSuggestCountryName(String text)',
  isAsync: true,
  run: async function(text) {
    let response = await fetch('https://restcountries.eu/rest/v2/name/' + text);
    return response.status === 200 ? (await response.json()).map(country => country['name']) : [];
  }
});
```

#### Subscribing to events

Demonstrates subscribing to platform events.

```javascript

   demog = grok.testData('demog', 5000);
   view = grok.addTableView(demog);
   
   function info(s) { grok.balloon.info(s); }
   
   demog.onValuesChanged((_) => info('ddt-values-changed'));
   demog.onCurrentRowChanged((_) => info('ddt-current-row-changed'));
   demog.onMouseOverRowChanged((_) => info('ddt-mouse-over-row-changed'));
   demog.onCurrentColChanged((_) => info('ddt-current-col-changed'));
   demog.onMouseOverColChanged((_) => info('ddt-mouse-over-col-changed'));
   demog.onCurrentCellChanged((_) => info('ddt-current-cell-changed'));
   demog.onMouseOverRowGroupChanged((_) => info('ddt-mouse-over-row-group-changed'));
   demog.onNameChanged((_) => info('ddt-table-name-changed'));
   demog.onMetadataChanged((_) => info('ddt-table-metadata-changed'));
   demog.onColumnNameChanged((_) => info('ddt-table-column-name-changed'));
   demog.onColumnSelectionChanged((_) => info('ddt-column-selection-changed'));
   demog.onColumnsChanged((_) => info('ddt-columns-changed'));
   demog.onColumnsAdded((_) => info('ddt-columns-added'));
   demog.onColumnsRemoved((_) => info('ddt-columns-removed'));
   demog.onRowsRemoved((_) => info('ddt-rows-removed'));
   demog.onDataChanged((_) => info('ddt-data-changed'));
   demog.onFilterChanged((_) => info('ddt-filter-changed'));
   demog.onSelectionChanged((_) => info('ddt-selection-changed'));
   
   grok.onProjectClosed(p => info(`${p.name}: closed`));
   grok.onProjectModified(p => info(`${p.name}: modified`));
   grok.onProjectOpened(p => info(`${p.name}: opened`));
   grok.onProjectSaved(p => info(`${p.name}: saved`));
   grok.onProjectUploaded(p => info(`${p.name}: uploaded`));
   
   grok.onCurrentProjectChanged(p => info(`Current project changed: ${grok.project.name}`));
   
   grok.onEvent('d4-current-view-changed', (_) => info('view changed'));
```

#### Creating custom viewers

Demonstrates integration of the D3-based sankey viewer (no Dart code whatsoever)
with the platform.

```javascript
let csv = `source,target,value
Barry,Elvis,2
Frodo,Elvis,2
Frodo,Sarah,2
Barry,Alice,2
Elvis,Sarah,2
Elvis,Alice,2
Sarah,Alice,4`;
let table = grok.parseCsv(csv);

let viewer = new SankeyViewer();
viewer.table = table;
viewer.options = {source: 'source', target: 'target', value: 'value'};
viewer.init();
grok.dockElement(viewer.root, 'Sankey Chart', 'left', 0.5);
```

#### Docking

Docking an arbitrary element

```javascript
let e = document.createElement('DIV');
e.innerText = 'This element has been created in JavaScript';
grok.dockElement(e, 'JS', 'left', 0.5);
```