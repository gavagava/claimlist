Ext.define('Claimlist.controller.Claims', {
    extend: 'Ext.app.Controller',
    stores: ['Claims'],
    models: ['Claim'],
    views: ['claim.List'],

    init: function() {
        this.control({
        	'claimlist > toolbar > button' : {
        		click: function () {
        			var store = this.getClaimsStore();
        	    	store.add({from: '', to: '', status: 'PROPOSED'});
        	    	store.load();
        		}
        	},
        	'actioncolumn': {
        		click: function (list, td, index) {
        			this.getClaimsStore().removeAt(index);
        		}
        	}
        });
    }
});