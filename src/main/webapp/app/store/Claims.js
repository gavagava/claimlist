Ext.define('Claimlist.store.Claims', {
    extend: 'Ext.data.Store',
    model: 'Claimlist.model.Claim',
    autoLoad: true,
    autoSync: true,
    
    proxy: {
    	type: 'rest',
    	url: 'rest/claims/',    	
    	reader: {
    		type: 'json'
    	}
    }
});