/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitter;

import java.io.IOException;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import java.awt.Desktop;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//import twitter4j.Tweet;


public class GetScore {
    	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GetScore.class);
        JFrame frame;
        JTextArea area;
    public boolean getTwitterScores()throws TwitterException, IOException
    {
        frame=new JFrame("Scores from Twitter");
           frame.setSize(500,300);
           area=new JTextArea(400,200);
            frame.setVisible(true);
            frame.setResizable(false);
            
         try {
            Twitter twitter = new TwitterFactory().getInstance();
            try {
                // get request token.
                // this will throw IllegalStateException if access token is already available
            	
                RequestToken requestToken = twitter.getOAuthRequestToken();
                LOG.info("Got request token.");
                LOG.info("Request token: " + requestToken.getToken());
                LOG.info("Request token secret: " + requestToken.getTokenSecret());
                AccessToken accessToken = null;

                while (null == accessToken) {
    				try {
    					URI uri=new URI(requestToken.getAuthorizationURL());
    					Desktop.getDesktop().browse(uri);
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (URISyntaxException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}

                    JFrame frame = new JFrame("Twitter PIN verification");

                    // prompt the user to enter their name
                    String pin = JOptionPane.showInputDialog(frame, "Enter the PIN");

                    
                    
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            LOG.error("Unable to get the access token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                LOG.info("Got access token.");
                LOG.info("Access token: " + accessToken.getToken());
                LOG.info("Access token secret: " + accessToken.getTokenSecret());
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                	return false;
                }
            }
             Query query = new Query("#GameMakerP532");
            
			    QueryResult result = twitter.search(query);
			    // commenting it out as it was giving a compile time error. creating a jira issue for this.
			    
			   // for  (Status tweet : result.getTweets()) {
			        
                          // area.append("@"+tweet.getUser().getName()+ ":" + tweet.getText()+"\n");
                            
			    //}
                            
                            
                            List <Status> status=result.getTweets();
                            for(Status st:status)
                              area.append("@"+st.getUser().getName()+ ":" + st.getText()+"\n");

            frame.add(area);
        } catch (TwitterException te) {
            te.printStackTrace();
            return false;
        }
        
                return true;

        
        
    }
}

