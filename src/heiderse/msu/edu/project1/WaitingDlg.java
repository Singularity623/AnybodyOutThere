package heiderse.msu.edu.project1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WaitingDlg extends DialogFragment {

	 /**
     * Called when the view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancel = true;
    }
    
	/**
     * Set true if we want to cancel
     */
    private boolean cancel = false;
    
    /**
     * Id for the game we are loading
     */
	private String gameId;
	
	public void setGameId(String id){
		gameId = id;
	}

	/**
     * Create the dialog box
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        cancel = false;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle(R.string.waiting);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            	cancel = true;
            }
        });

        final RelativeLayout view = (RelativeLayout)getActivity().findViewById(R.id.a);
        final AlertDialog dlg = builder.create();

        
        new Thread(new Runnable() {

            @Override
            public void run() {
                // Create a cloud object and get the XML
                Service service = new Service();
                boolean isGameReady = false;
                
                while (!isGameReady && !cancel){
                	// Send request every 2 seconds
                	try {
                		Thread.sleep(2000);
                	}
                	catch (InterruptedException ex){
                	}
                	isGameReady = service.isGameReady(gameId);
                }
                
                final boolean fail = !isGameReady;
                	
            	view.post(new Runnable() {

                    @Override
                    public void run() {
                        dlg.dismiss();
                        if(fail) {
                            Toast.makeText(view.getContext(), R.string.join_fail, Toast.LENGTH_SHORT).show();
                            // TODO: Go back to Mainactivity
                        } else {
                            // Success!
                        	// Join a game
                        	Toast.makeText(view.getContext(), R.string.join_success, Toast.LENGTH_SHORT).show();
                        	
                            if(getActivity() instanceof WaitingActivity) {
                                ((WaitingActivity)getActivity()).joinGame();
                            }
                        }
                        
                    }
                
                });
             	
                
                
            }
        }).start();
        
        return dlg;
    }

}
