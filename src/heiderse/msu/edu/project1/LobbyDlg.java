package heiderse.msu.edu.project1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ListView;

public class LobbyDlg extends DialogFragment {

	private boolean _foundPlayer = false;
	

	
	
	public boolean GetPlayerFound() {
		return _foundPlayer;
	}

	public void set_foundPlayer(boolean _foundPlayer) {
		this._foundPlayer = _foundPlayer;
	}

	public LobbyDlg() {
		// TODO Auto-generated constructor stub
	}

    /**
     * Create the dialog box
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle(R.string.lobby);

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
       
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.player_dlg, null);
        builder.setView(view);
        
        
        final AlertDialog dlg = builder.create();
        
        // Find the list view
        ListView list = (ListView)view.findViewById(R.id.listPlayers);
        
        
        /**TODO**/
        /*
         * Create call to service class to create a thread to poll server for players.
         */
        
        
        // Create an adapter
        //final Cloud.CatalogAdapter adapter = new Cloud.CatalogAdapter(list);
        //list.setAdapter(adapter);
        
       /* list.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	String catId = adapter.getId(position);
            			
                // Dismiss the dialog box
                dlg.dismiss();
                
                LoadingDlg loadDlg = new LoadingDlg();
                loadDlg.setCatId(catId);
                loadDlg.show(getActivity().getSupportFragmentManager(), "loading");
            }
            
        });*/
        
        return dlg;
    }
}
