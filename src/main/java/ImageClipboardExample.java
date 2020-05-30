import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.WindowConstants;

public class ImageClipboardExample extends JPanel {
	
	private final Image image;

	public ImageClipboardExample(Image image) {
		this.image = image;
		setLayout(new BorderLayout());
		add(new JLabel(new ImageIcon(image)), BorderLayout.CENTER);
		JButton copyButton = new JButton("Copy");
		copyButton.addActionListener(e -> copyToClipboard());
		add(copyButton, BorderLayout.SOUTH);
		setTransferHandler(new TransferHandler("image") {
			@Override
			protected Transferable createTransferable(JComponent c) {
				return new Transferable() {
					@Override
					public boolean isDataFlavorSupported(DataFlavor flavor) {
						return flavor == DataFlavor.imageFlavor;
					}

					@Override
					public DataFlavor[] getTransferDataFlavors() {
						return new DataFlavor[] { DataFlavor.imageFlavor };
					}

					@Override
					public Object getTransferData(DataFlavor flavor) {
						return image;
					}
				};
			}
		});
	}

	public Image getImage() {
		return image;
	}

	public void copyToClipboard() {
		getTransferHandler().exportToClipboard(this, Toolkit.getDefaultToolkit().getSystemClipboard(), TransferHandler.COPY);
	}

	public static void main(String[] args) throws IOException {
		Image image = ImageIO.read(ImageClipboardExample.class.getResourceAsStream("/cropped-java-craftsman-duke.png"));
		JFrame frame = new JFrame();
		frame.add(new ImageClipboardExample(image));
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
