//полифилим обещания, на случай если браузер их не поддерживает
ES6Promise.polyfill();

Ext.application({
    requires: ['Ext.container.Viewport'],
    name: 'Claimlist',

    appFolder: 'app',
    controllers: ['Claims'],

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