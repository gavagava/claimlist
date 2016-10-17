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
            	autoLoad: true,
            	fields: [{name: 'name', convert: function (value, record) { return record.raw; }}],
            	proxy: {
            		type: 'ajax',
            		url: url
            	}
            });
    	}
    	
        this.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 1
        });
        
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
                    store: getSimpleStore('rest/from'),
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
                    store: getSimpleStore('rest/to'),
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
                    store: getSimpleStore('rest/statuses'),
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
                    scope: this
                }]
            }],
            selModel: {
                selType: 'cellmodel'
            },
            tbar: [{
                text: 'Add Claim',
                scope: this
            }]
        });

        this.callParent(arguments);
    }
});