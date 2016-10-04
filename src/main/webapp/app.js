//полифилим обещания, на случай если браузер их не поддерживает
ES6Promise.polyfill();

Ext.application({
    requires: ['Ext.container.Viewport'],
    name: 'Claimlist',

    appFolder: 'app',
    models: ['Claim'],
    stores: ['Claims'],
    views: ['claim.List'],

    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [
                {
                    xtype: 'claimlist'
                }
            ]
        });
    }
});