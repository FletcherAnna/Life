package life;

	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.util.Random;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.Timer;


	public class Pane extends JPanel {
		
		
	    
	    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
			private Cell grid[][];
	    	private Boolean results[][];
	    	private static final Random rnd = new Random();
	    	
	    	
	        public Pane(int len) {
	            this.grid = new Cell[len][len];
	            this.results = new Boolean[len][len];
	            
	            GridLayout gridLayout = new GridLayout(len, len);
	            this.setLayout(gridLayout);
	            setupGrid();
	        }
	        
	        private void setupGrid() {
	        	for (int i = 0; i < grid.length; i++) {
	                for (int j = 0; j < grid[i].length; j++) {
	                    grid[i][j] = (rnd.nextDouble() < 0.80) ? new Cell(false) : new Cell(true);
	                    Cell c = grid[i][j];
	                    this.add(grid[i][j]);
	                    grid[i][j].addActionListener(new ActionListener() {
	                        public void actionPerformed(ActionEvent e) {
	                        c.setLive(true);
	                        }
	                });
	            }}
			}

			public void updateGrid() {
	            for (int i = 0; i < grid.length; i++) {
	                for (int j = 0; j < grid[i].length; j++) {
	                    results[i][j] = applyRule(i, j);
	                }
	            }
	            
	            for (int i = 0; i < grid.length; i++) {
	                for (int j = 0; j < grid[i].length; j++) {
	                    grid[i][j].setLive(results[i][j]);
	                }
	            }
	        }
	        
	        private Boolean applyRule(int x, int y) {
	        	int width = grid.length;
	        	int count = grid[Math.floorMod(x-1, width)][y].getInt() + grid[Math.floorMod(x+1, width)][y].getInt() + grid[x][Math.floorMod(y-1, width)].getInt() + grid[x][Math.floorMod(y+1, width)].getInt() + grid[Math.floorMod(x-1, width)][Math.floorMod(y-1, width)].getInt() + grid[Math.floorMod(x+1, width)][Math.floorMod(y-1, width)].getInt() + grid[Math.floorMod(x-1, width)][Math.floorMod(y+1, width)].getInt() + grid[Math.floorMod(x+1, width)][Math.floorMod(y+1, width)].getInt();
	            
	        	if (grid[x][y].getLive() && (count == 2 || count == 3)) {
	        		return true;
	        	} else if(!grid[x][y].getLive() && count == 3) {
	        		return true;
	        	} else {
	        		return false;
	        	}
	        }

	    public class Cell extends JButton {
	    	private Boolean live;
	        public Cell(Boolean live) {
	        	this.live = live;
	            setContentAreaFilled(false);
	            setBorderPainted(false);
	            setBackground(live ? Color.black : Color.GREEN);
	            setOpaque(true);
	        }
	        
	        public void setLive(Boolean live) {
	        	this.live = live;
	        	this.setBackground(live ? Color.black : Color.GREEN);
	        	super.repaint();
	        }
	        
	        public Boolean getLive() {
	        	return live;
	        }
	        
	        public int getInt() {
	        	return live ? 1 : 0;
	        }

	        @Override
	        public Dimension getPreferredSize() {
	            return new Dimension(4, 4);
	        }

	    }


	
	public static void main(String[] args) {        
        final Pane c = new Pane(125);
        JFrame frame = new JFrame();
        frame.getContentPane().add(c);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        new Timer(65, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                c.updateGrid();
                c.repaint();
            }
        }).start();
    }

}
