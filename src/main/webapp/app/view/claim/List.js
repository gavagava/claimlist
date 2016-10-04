Ext.define('Claimlist.view.claim.List' ,{
    extend: 'Ext.grid.Panel',
    requires: [
               'Ext.selection.CellModel',
               'Ext.grid.*',
               'Ext.data.*',
               'Ext.util.*',
               'Ext.form.*',
               'Claimlist.model.Claim'
           ],
    alias: 'widget.claimlist',

    title: 'All Claims',
    
    initComponent: function() {
    	
    	function getSimpleStore (url) {
    		return new Ext.data.Store({
            	autoLoad: false,
            	fields: ['name']            	
            });
    	}
    	
        this.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 1
        });
        
        this.fromStore = getSimpleStore();
        
        this.toStore = getSimpleStore();
        
        this.statusStore = getSimpleStore();
        
        Ext.apply(this, {
            width: '100%',
            height: '100%',
            plugins: [this.cellEditing],
            store: 'Claims',
            columns: [{
                header: 'ID',
                dataIndex: 'id',
                width: 30
            }, {
                header: 'From',
                dataIndex: 'from',
                flex: 1,
                editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    displayField: 'name',
                    valueField: 'name',
                    store: this.fromStore,
                    queryMode: 'local'
                })
            }, {
                header: 'To',
                dataIndex: 'to',
                flex: 1,                
                editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    displayField: 'name',
                    valueField: 'name',
                    store: this.toStore,
                    queryMode: 'local'
                })
            }, {
                header: 'Status',
                dataIndex: 'status',
                flex: 1,                
                editor: new Ext.form.field.ComboBox({
                    triggerAction: 'all',
                    editable: false,
                    displayField: 'name',
                    valueField: 'name',
                    store: this.statusStore,
                    queryMode: 'local'
                })
            }, {
                xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    icon: 'icons/delete.gif',
                    tooltip: 'Delete Claim',
                    scope: this,
                    handler: this.onRemoveClick
                }]
            }],
            selModel: {
                selType: 'cellmodel'
            },
            tbar: [{
                text: 'Add Claim',
                scope: this,
                handler: this.onAddClick
            }]
        });

        this.callParent(arguments);
        
        this.on('afterlayout', this.loadStore, this, {
            delay: 1,
            single: true
        });
    },
    
    loadStore: function () {
    	//сначала руками загружаем все справочники т.к. Ext 4 не умеет нормально работать с простыми массивами
    	//т.к. вроде нет никаких ограничений на поддерживаемые браузеры, воспользуемся промайсами и лямбдами для краткости и будем надеется, что у нас достаточно свежий хром :)
    	var loadRef = function (url, store) {
    		//вернется обещание, резолвящееся после загрузки данных справочника
    		return new Promise(function (resolve, reject) {
    			Ext.Ajax.request({
    				url: url,
    				success: function (response) {
    					var arr = JSON.parse(response.responseText);
    					store.loadData(arr.map(function (item) { return { name: item }; }));
    					resolve(response);
    				}
    			});
    		});
    	};
    	
    	Promise.all([loadRef('rest/from', this.fromStore), 
    			loadRef('rest/to', this.toStore), 
    			loadRef('rest/statuses', this.statusStore)])
    			.then(function () {
    				this.getStore().load();
    			}.bind(this));
    },
    
    onAddClick: function () {
    	var store = this.getStore();
    	store.add({from: '', to: '', status: 'PROPOSED'});
    	store.load();
    },
    
    onRemoveClick: function (grid, rowIndex) {
    	this.getStore().removeAt(rowIndex);
    }
});