package view.imagePanel;

import imagewizard.MyFilter;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Resources;
import net.miginfocom.swing.MigLayout;
import utility.Constants;
import utility.Util;
import view.communication.ClientHandler;



public class ImagePanel implements ActionListener,ChangeListener {

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ImagePanel.class);

	private JPanel imagePanel;
	private JPanel imageTiles ;
	private JPanel paginationPanel;
	private static final int imagesPerPage = 14;
	private List<ImageProperties> allImages;
	private List<ImageProperties> presentImages;
	private int presentPage = 1;
	private int imageSize = 50;
	private int imageHeight = 50;
	private JScrollPane imageTilesScrollPane;
	private JComboBox imageTags;
	private ImageActionListener imageActionListener;
	private int totalImages;

	private final String host = Constants.HOST;
    private final String path = Constants.PATH+"/listPageResources";
	


	public ImagePanel(ImageActionListener imageActionListener){
		this.imageActionListener = imageActionListener;
		JPanel propertiesPanel = createPropertiesPanel();
		getImages(); //change this
		totalImages = ClientHandler.countTag(null, Constants.HOST, Constants.PATH+"/countTag", new Exception[1]);
		int lastIndex = allImages.size() < imagesPerPage ? allImages.size():imagesPerPage;
		presentImages = allImages.subList(0, lastIndex);
		imageTiles = new JPanel(new GridLayout(7,2));
		populateImageTiles();		
		imageTilesScrollPane = new JScrollPane(imageTiles, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		paginationPanel = new JPanel(new FlowLayout());
		populatePaginationPanel();
		imagePanel = new JPanel();
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
		propertiesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		imagePanel.add(propertiesPanel);
		imagePanel.add(imageTilesScrollPane,"span");
		imagePanel.add(paginationPanel,"span");
		JButton upload = new JButton("Upload Images");
		upload.addActionListener(this);
		imagePanel.add(upload);
		imagePanel.setSize(200, 400);
	}

	private JPanel createPropertiesPanel() {
		MigLayout migLayout = new MigLayout();
		JPanel propPanel = new JPanel(migLayout);
		
		JLabel tags = new JLabel("Image Tags");
		propPanel.add(tags);
		String[] tagNames = getImageTags();
		imageTags = new JComboBox(tagNames);
		imageTags.addActionListener(this);
		propPanel.add(imageTags,"wrap");
		return propPanel;
	}

	private String[] getImageTags() {
		//String[] tags = {"All", "Tanks", "Bricks", "Enter tag name:"};
		String tags[] = ClientHandler.listTags(Constants.HOST, Constants.PATH+"/getAllTags", new Exception[1]);
		String[] finalTags = new String[tags.length+1];
		finalTags[0] = new String("All");
		for(int i = 1;i<tags.length;i++){
			finalTags[i] = new String(tags[i]);
		}
		return finalTags;
	}

	private void populatePaginationPanel() {
		int size = totalImages;
		paginationPanel.removeAll();
		JSlider imageResizeSlider = new JSlider(JSlider.HORIZONTAL,10,100,50);
		imageResizeSlider.addChangeListener(this);
		paginationPanel.add(imageResizeSlider);
		JButton first = new JButton("<<");
		first.addActionListener(this);
		JButton prev = new JButton("<");
		prev.addActionListener(this);
		JButton next = new JButton(">");
		next.addActionListener(this);
		JButton last = new JButton(">>");
		last.addActionListener(this);
		paginationPanel.add(first);
		paginationPanel.add(prev);
		int noOfPages = (int) Math.ceil((double)size/(double)imagesPerPage);
		for(int i = 1; i <= noOfPages ; i++){
			JButton b = new JButton(String.valueOf(i));
			b.addActionListener(this);
			paginationPanel.add(b);
		}
		paginationPanel.add(next);
		paginationPanel.add(last);

	}

	private void populateImageTiles() {
		String tag = (String) imageTags.getSelectedItem();
		if(tag.equalsIgnoreCase("All")){
			tag = null;
		}
		imageTiles.removeAll();
		/*Exception[] exceptions = new Exception[1];
		Resources[] images = ClientHandler.listPageResources(String.valueOf(presentPage), String.valueOf(imagesPerPage),
				tag, host, "/GameMakerServer/listPageResources", exceptions);*/
		for(int i = 0; i < allImages.size(); i++){
			Image image = allImages.get(i).getImage();
			ImageIcon icon = new ImageIcon(image.getScaledInstance(imageSize, imageSize, 1));
            JButton button = new JButton(icon);
            button.setName(allImages.get(i).getImageKey());
            button.addActionListener(imageActionListener);
            imageTiles.add(button);
			
		}
		/*for(ImageProperties i:presentImages){
			ImageIcon icon = new ImageIcon(i.getImage().getScaledInstance(imageSize, imageSize, 1));
<<<<<<< HEAD
            JButton button = new JButton(icon);
            button.setName(i.getImageKey());
            button.addActionListener(imageActionListener);
            imageTiles.add(button);
		}*/
		
		}


	//	public JPanel createImagePanel() {
	//
	//		return mainImagePanel;
	//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		ImagePanel panel = new ImagePanel(null);
		//JPanel ipanel = panel.createImagePanel();
		f.setContentPane(panel.getImagePanel());
		f.setVisible(true);
		f.setSize(600, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public List<ImageProperties> getImages(){
//		List<ImageProperties> images = new ArrayList<ImageProperties>();
//		URL jar = this.getClass().getClassLoader().getResource("resource.jar");
//		List<String> list = new ArrayList<String>();
//
//		ZipInputStream zip;
//		try {
//			zip = new ZipInputStream(jar.openStream());
//			ZipEntry ze = null;
//
//			while ((ze = zip.getNextEntry()) != null) {
//				String entryName = ze.getName();
//				if (entryName.endsWith(".png") || entryName.endsWith(".jpg")) {
//					list.add(entryName);
//					Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(entryName));
//					ImageProperties imp = new ImageProperties("/"+entryName,"All",image);
//					images.add(imp);
//				}
//			}
//		} catch (IOException e1) {
//		}
//
//		/* for (int i = 0; i < list.size(); i++) {
//
//	            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(list.get(i)));
//	            images.add(image);
//
//	        }*/
//		return images;
		String tag = (String) imageTags.getSelectedItem();
		if(tag.equalsIgnoreCase("all")){
			tag = null;
		}
		allImages = new ArrayList<ImageProperties>();
		Exception[] exceptions = new Exception[1];
		Resources[] images = ClientHandler.listPageResources(String.valueOf(presentPage), String.valueOf(imagesPerPage),
				tag, Constants.HOST, Constants.PATH+"/listPageResources", exceptions);
		for(int i = 0; i < images.length; i++){
			Image image = Util.convertByteArraytoImage(images[i].getResource(), "jpg");
			ImageProperties im = new ImageProperties(String.valueOf(images[i].getReourceNumber()),
					images[i].getResourceName(), image);
			allImages.add(im);
		}
		return allImages;
		
	}
	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().contentEquals("<<")){
			if(presentPage == 1){
				return;
			}
			presentPage = 1;
			getImages();
			updateImageTiles();
			return;
		}
		else if(e.getActionCommand().contentEquals("<")){
			if(presentPage == 1){
				return;
			}
			presentPage--;
			getImages();
			updateImageTiles();
			return;
		}
		else if(e.getActionCommand().contentEquals(">")){
			if(presentPage == Math.ceil((double)totalImages/(double)imagesPerPage)){
				return;
			}
			presentPage++;
			getImages();
			updateImageTiles();
			return;
		}
		else if(e.getActionCommand().contentEquals(">>")){
			if(presentPage == Math.ceil((double)totalImages/(double)imagesPerPage)){
				return;
			}
			presentPage = (int) Math.ceil((double)totalImages/(double)imagesPerPage);
			getImages();
			updateImageTiles();
			return;
		}
		else if(e.getActionCommand().equalsIgnoreCase("Upload Images")){
			handleUpload();
		}
		else if(e.getSource() == imageTags){
			presentPage = 1;
			String tag = (String)imageTags.getSelectedItem();
			totalImages = ClientHandler.countTag(tag, Constants.HOST, Constants.PATH+"/countTag", new Exception[1]);
			getImages();
			updateImageTiles();
			populatePaginationPanel();
			paginationPanel.revalidate();
			paginationPanel.repaint();

		}
		else{
			try{
				int num = Integer.parseInt(e.getActionCommand());
				presentPage = num;
				getImages();
				updateImageTiles();
				return;
			}catch (NumberFormatException ex){

			}
		}


	}

	private void handleUpload() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setFileFilter(new MyFilter());
		File file = null;
		JPanel previewPanel = new JPanel(new MigLayout());
		int returnVal = jFileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = jFileChooser.getSelectedFile();
			try {
				Image image = ImageIO.read(file);
				String fileName =file.getName();
				int dotposition= fileName.lastIndexOf(".");

				String imagetype  = fileName.substring(dotposition + 1, fileName.length()); 
				ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 150, 1));
				JButton button = new JButton(icon);
				previewPanel.add(button,"wrap");
				JLabel tag = new JLabel("Enter tag name:");
				JTextField tagInput = new JTextField(10);
				previewPanel.add(tag);
				previewPanel.add(tagInput,"wrap");
				int ret = JOptionPane.showConfirmDialog(null, previewPanel,"Preview",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
				if(ret == JOptionPane.OK_OPTION){
					uploadImageToDb(image,tagInput.getText(),imagetype);
				}

			} catch (IOException e1) {
				LOG.error("Unable to read the file");
			}
		}
	}

	private void uploadImageToDb(Image image, String tag, String imagetype) {
		Resources resources = new Resources();
		resources.setResourceType("image");
		//TAG: replace the tag with a filename later
		resources.setResourceName(tag);
		//TAG: allow for the logged in user to pass on the name later
		resources.setUsername("admin");
		resources.setResource(Util.convertImagetoByteArray(image, imagetype));
		ClientHandler.saveResource(resources, Constants.HOST, Constants.PATH+"/saveResource", new Exception[1]);
	}

	public JPanel getImagePanel(){
		return this.imagePanel;
	}

	private void updateImageTiles(){
		/*int firstIndex = imagesPerPage*(presentPage-1);
		int lastIndex = allImages.size() < (firstIndex + imagesPerPage) ? allImages.size(): (firstIndex + imagesPerPage);
		presentImages = allImages.subList(firstIndex, lastIndex);*/
		populateImageTiles();
		imageTiles.revalidate();
		imagePanel.repaint();
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider s = (JSlider) e.getSource();
		imageSize = s.getValue();
		updateImageTiles();

	}



}
