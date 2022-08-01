package project2ia;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View extends javax.swing.JFrame {

    private GridLayout layout;
    private int rows;
    private int columns;
    private World world;
    private Vector<JButton> buttonsVector = new Vector();
    private int userPoints;
    private int machinePoints;
    private int maxDepthSetByUser;

    public View() {
        initComponents();
        this.rows = 8;
        this.columns = 8;
        this.userPoints = 0;
        this.machinePoints = 0;
        this.maxDepthSetByUser = 0;
        this.levelComboBox.addItem("Beginner");
        this.levelComboBox.addItem("Amateur");
        this.levelComboBox.addItem("Expert");

        this.humanPointsLabel.setEditable(false);
        this.machinePointsLabel.setEditable(false);

        layout = new GridLayout(rows, columns);

        this.dashboardPanel.setLayout(layout);
        this.world = new World(rows, columns);
        this.world.randomWorld();

        int id = 0;
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                JButton newButton = new JButton();
                newButton.setEnabled(false);

                String buttonName = id + "";
                newButton.setActionCommand(buttonName);
                newButton.setBackground(Color.WHITE);

                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        buttonDashboardAction(event);
                    }
                });
                this.dashboardPanel.add(newButton);
                this.buttonsVector.add(newButton);
                id++;

            }
        }
        this.showWorld(this.world);
        this.setVisible(true);

    }

    public void showWorld(World world) {
        System.out.println("Showing World: ");
        world.printWorld();
        int positionButton = 0;
        for (int row = 0; row < world.getMatrix().length; row++) {
            for (int column = 0; column < world.getMatrix()[row].length; column++) {
                buttonsVector.get(positionButton).setIcon(this.paintElements(world.getMatrix()[row][column]));
                buttonsVector.get(positionButton).setDisabledIcon(this.paintElements(world.getMatrix()[row][column]));
                positionButton++;
            }
        }
    }

    public ImageIcon paintElements(int element) {
        switch (element) {
            case 0:
                ImageIcon blankSpace = new ImageIcon(getClass().getResource("img\\empty.gif"));
                return blankSpace;
            case 1:
                ImageIcon userBlackHorse = new ImageIcon(getClass().getResource("img\\blackHorse.png"));
                return userBlackHorse;
            case 2:
                ImageIcon machineWhiteHorse = new ImageIcon(getClass().getResource("img\\whiteHorse.png"));
                return machineWhiteHorse;
            case 3:
                ImageIcon flower = new ImageIcon(getClass().getResource("img\\flower.png"));
                return flower;
            case 4:
                ImageIcon grass = new ImageIcon(getClass().getResource("img\\grass.png"));
                return grass;
            case 5:
                ImageIcon apple = new ImageIcon(getClass().getResource("img\\apple.png"));
                return apple;
            default:
                ImageIcon blankSpaceDefault = new ImageIcon(getClass().getResource("img\\empty.gif"));
                return blankSpaceDefault;
        }
    }

    public void setPointsByPosition(int buttonPositionWorld, TypePlayer playerType) {
        // If the next button is a flower
        if (buttonPositionWorld == 3) {
            if (playerType.equals(TypePlayer.MACHINE)) {
                this.machinePoints += 3;
            } else {
                this.userPoints += 3;
            }
        }
        // If the next button is grass
        if (buttonPositionWorld == 4) {
            if (playerType.equals(TypePlayer.MACHINE)) {
                this.machinePoints++;
            } else {
                this.userPoints++;
            }
        }
        // If the next button is an apple
        if (buttonPositionWorld == 5) {
            if (playerType.equals(TypePlayer.MACHINE)) {
                this.machinePoints += 5;
            } else {
                this.userPoints += 5;
            }
        }
        this.humanPointsLabel.setText(this.userPoints + "");
        this.machinePointsLabel.setText(this.machinePoints + "");
    }

    public void deactivateButtons() {
        for (int button = 0; button < this.buttonsVector.size(); button++) {
            this.buttonsVector.get(button).setEnabled(false);
        }
    }

    // Checks if the game isn't over yet. The game is still on, while any items still left in the world
    // If not, there is no move to make for any of the players
    public boolean areStillAnyItemsInWorld() {
        boolean itemsInWorld = false;
        for (int row = 0; row < this.world.getWidth(); row++) {
            for (int column = 0; column < this.world.getHeight(); column++) {
                if (this.world.getMatrix()[row][column] == 3 || this.world.getMatrix()[row][column] == 4 || this.world.getMatrix()[row][column] == 5) {
                    itemsInWorld = true;
                }
            }
        }
        return itemsInWorld;
    }

    public void whoWon() {
        if (this.machinePoints > this.userPoints) {
            JOptionPane.showMessageDialog(this, "WINNER: Machine");
        } else if (this.userPoints > this.machinePoints) {
            JOptionPane.showMessageDialog(this, "WINNER: User");
        } else if (this.userPoints == this.machinePoints) {
            JOptionPane.showMessageDialog(this, "TIE: There is no winner");
        }

        this.startGameButton.setText("Play Again");
        this.startGameButton.setEnabled(true);
    }

    public void activateButtonsForPossibleMoves(Vector<Coordinate> possibleMoves) {
        int buttonId = 0;
        for (int move = 0; move < possibleMoves.size(); move++) {
            for (int row = 0; row < this.world.getWidth(); row++) {
                for (int column = 0; column < this.world.getHeight(); column++) {
                    if (possibleMoves.get(move).getX() == row && possibleMoves.get(move).getY() == column) {
                        this.dashboardPanel.getComponent(buttonId).setEnabled(true);
                    }
                    buttonId++;
                }
            }
        }
    }

    public void userCheckMoves() {
        Vector<Coordinate> userPossibleMoves = this.possibleMovesUser();
        this.activateButtonsForPossibleMoves(userPossibleMoves);
    }

    public void machineMove() {
        Node rootNode = new Node();
        rootNode.setHumanPoints(this.userPoints);
        rootNode.setMachinePoints(this.machinePoints);

        // Send copy of the world to calculate the minmax
        //World copyWorld = new World(this.world.getWidth(), this.world.getWidth()); // Did not work
        //copyWorld.setMatrix(this.world.getMatrix()); // Did not work
        //World copyWorld = new World(this.world); // Did not work
        try {
            World clonedWorld = (World) this.world.clone();
            // Checks if the current world has a different reference of the cloned one
            //System.out.println(this.world.equals(clonedWorld));

            System.out.println("World before sending to Node: ");
            //clonedWorld.printWorld();
            rootNode.setWorld(clonedWorld);

            MinMax minMax = new MinMax(rootNode, this.maxDepthSetByUser);
            System.out.println("WORLD before minmax decision: ");
            this.world.printWorld();

            // Next move
            Coordinate coordinateMachineMove = minMax.move();

            System.out.println("MINMAX Decision: (" + coordinateMachineMove.getX() + " , " + coordinateMachineMove.getY() + ")");
            System.out.println("WORLD after minmax decision: ");
            this.world.printWorld();

            // Finds the current machine-player coordinate in the world, to then, set it
            Coordinate machinePosition = new Coordinate();
            for (int row = 0; row < this.world.getWidth(); row++) {
                for (int column = 0; column < this.world.getHeight(); column++) {
                    if (this.world.getMatrix()[row][column] == 2) {
                        System.out.println("Found Machine!!");
                        machinePosition.setX(row);
                        machinePosition.setY(column);
                    }
                }
            }

            // Set points according to the next position where the machine player wants to move next
            this.setPointsByPosition(this.world.getMatrix()[coordinateMachineMove.getX()][coordinateMachineMove.getY()], TypePlayer.MACHINE);

            System.out.println("WORLD before setting positions: ");
            this.world.printWorld();
            // Set positions next move and previous position
            //this.world.getMatrix()[coordinateMachineMove.getX()][coordinateMachineMove.getY()] = 2;
            //this.world.getMatrix()[machinePosition.getX()][machinePosition.getY()] = 0;

            System.out.println("WORLD after setting positions: ");
            this.world.printWorld();

            this.showWorld(this.world);

            if (!this.areStillAnyItemsInWorld()) {
                this.whoWon();
            } else {
                this.possibleMovesUser();
            }
        } catch (CloneNotSupportedException ex) {
            System.out.print(ex);
        }

    }

    public Vector<Coordinate> possibleMovesUser() {
        Coordinate userPosition = new Coordinate();

        for (int row = 0; row < this.world.getWidth(); row++) {
            for (int column = 0; column < this.world.getHeight(); column++) {
                if (this.world.getMatrix()[row][column] == 1) {
                    userPosition.setX(row);
                    userPosition.setY(column);
                }
            }
        }

        Vector<Coordinate> userPossibleMoves = new Vector();

        int twoStepsRight = userPosition.getX() + 2;
        int twoStepsDown = userPosition.getY() + 2;
        int twoStepsUp = userPosition.getY() - 2;
        int twoStepsLeft = userPosition.getX() - 2;
        int oneStepRight = userPosition.getX() + 1;
        int oneStepDown = userPosition.getY() + 1;
        int oneStepUp = userPosition.getY() - 1;
        int oneStepLeft = userPosition.getY() - 1;

        if (twoStepsRight < this.world.getWidth() && oneStepUp >= 0 && (this.world.isThereAnyHorse(twoStepsRight, oneStepUp) == false)) {
            Coordinate twoRightOneUp = new Coordinate(twoStepsRight, oneStepUp);
            userPossibleMoves.add(twoRightOneUp);
        }
        if (twoStepsRight < this.world.getWidth() && oneStepDown < this.world.getHeight() && (this.world.isThereAnyHorse(twoStepsRight, oneStepDown) == false)) {
            Coordinate twoRightOneDown = new Coordinate(twoStepsRight, oneStepDown);
            userPossibleMoves.add(twoRightOneDown);
        }
        if (twoStepsDown < this.world.getHeight() && oneStepLeft >= 0 && (this.world.isThereAnyHorse(oneStepLeft, twoStepsDown) == false)) {
            Coordinate oneLeftTwoDown = new Coordinate(oneStepLeft, twoStepsDown);
            userPossibleMoves.add(oneLeftTwoDown);
        }
        if (twoStepsDown < this.world.getHeight() && oneStepRight < this.world.getWidth() && (this.world.isThereAnyHorse(oneStepRight, twoStepsDown) == false)) {
            Coordinate oneRightTwoDown = new Coordinate(oneStepRight, twoStepsDown);
            userPossibleMoves.add(oneRightTwoDown);
        }
        if (twoStepsUp >= 0 && oneStepLeft >= 0 && (this.world.isThereAnyHorse(oneStepLeft, twoStepsUp) == false)) {
            Coordinate oneLeftTwoUp = new Coordinate(oneStepLeft, twoStepsUp);
            userPossibleMoves.add(oneLeftTwoUp);
        }
        if (twoStepsUp >= 0 && oneStepRight < this.world.getWidth() && (this.world.isThereAnyHorse(oneStepRight, twoStepsUp) == false)) {
            Coordinate oneRigthTwoUp = new Coordinate(oneStepRight, twoStepsUp);
            userPossibleMoves.add(oneRigthTwoUp);
        }
        if (twoStepsLeft >= 0 && oneStepUp >= 0 && (this.world.isThereAnyHorse(twoStepsLeft, oneStepUp) == false)) {
            Coordinate twoLeftOneUp = new Coordinate(twoStepsLeft, oneStepUp);
            userPossibleMoves.add(twoLeftOneUp);
        }
        if (twoStepsLeft >= 0 && oneStepDown < this.world.getHeight() && (this.world.isThereAnyHorse(twoStepsLeft, oneStepDown) == false)) {
            Coordinate twoLeftOneDown = new Coordinate(twoStepsLeft, oneStepDown);
            userPossibleMoves.add(twoLeftOneDown);
        }

        return userPossibleMoves;

    }

    private void buttonDashboardAction(ActionEvent event) {
        Coordinate buttonCoordinate = new Coordinate();

        int buttonId = Integer.parseInt(event.getActionCommand());
        int iteratorButtonId = 0;

        // Finds the coordinate of that specific button when the action is performed
        for (int row = 0; row < this.world.getWidth(); row++) {
            for (int column = 0; column < this.world.getHeight(); column++) {
                if (iteratorButtonId == buttonId) {
                    buttonCoordinate.setX(row);
                    buttonCoordinate.setY(column);
                }
                iteratorButtonId++;
            }
        }

        // Set points according to the button where the user player wants to move next
        this.setPointsByPosition(this.world.getMatrix()[buttonCoordinate.getX()][buttonCoordinate.getY()], TypePlayer.USER);

        // Finds the current user-player coordinate in the world, to then, set it
        Coordinate userPosition = new Coordinate();
        for (int row = 0; row < this.world.getWidth(); row++) {
            for (int column = 0; column < this.world.getHeight(); column++) {
                if (this.world.getMatrix()[row][column] == 1) {
                    userPosition.setX(row);
                    userPosition.setY(column);
                }
            }
        }

        // Show moves in the view, sets the previous user position to 0
        // And the new position to 1 which represents the user
        this.world.getMatrix()[buttonCoordinate.getX()][buttonCoordinate.getY()] = 1;
        this.world.getMatrix()[userPosition.getX()][userPosition.getY()] = 0;

        // Deactivate all buttons
        this.deactivateButtons();

        // Check if someone won, if not, let the machine play
        if (!this.areStillAnyItemsInWorld()) {
            this.whoWon();
        } else {
            Thread threadMachine = new Thread() {
                public void run() {
                    try {
                        sleep(1500);
                        // machinePlays
                    } catch (InterruptedException ex) {
                        Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            threadMachine.start();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        levelComboBox = new javax.swing.JComboBox();
        startGameButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        machinePointsLabel = new javax.swing.JTextField();
        humanPointsLabel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Menu");

        levelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelComboBoxActionPerformed(evt);
            }
        });

        startGameButton.setText("Start Game");
        startGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Points:");

        jLabel6.setText("White Horse (Machine):");

        jLabel7.setText("Black Horse (User):");

        machinePointsLabel.setText("0");
        machinePointsLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                machinePointsLabelActionPerformed(evt);
            }
        });

        humanPointsLabel.setText("0");
        humanPointsLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                humanPointsLabelActionPerformed(evt);
            }
        });

        jLabel2.setText("White Horse: Machine");

        jLabel4.setText("Black Horse: User");

        jLabel8.setText("Grass:  1 point");

        jLabel9.setText("Flower: 3 points");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Statistics:");

        jLabel11.setText("Apple:  5 points");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Level:");

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11))
                        .addGap(34, 34, 34)
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(humanPointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(machinePointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(levelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(startGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))))
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(machinePointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(humanPointsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(levelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startGameButton))
                .addContainerGap(147, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(dashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void levelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_levelComboBoxActionPerformed

    private void startGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGameButtonActionPerformed
        if (this.startGameButton.getText() == "Play Again") {
            this.world.randomWorld();
            this.showWorld(world);
            this.userPoints = 0;
            this.machinePoints = 0;
            this.humanPointsLabel.setText("0");
            this.machinePointsLabel.setText("0");
            this.startGameButton.setText("Start Game");
            this.levelComboBox.setEnabled(true);
        } else {
            if (this.levelComboBox.getSelectedItem() == "Beginner") {
                this.maxDepthSetByUser = 2;
                this.machineMove();
            } else if (this.levelComboBox.getSelectedItem() == "Amateur") {
                this.maxDepthSetByUser = 4;
                this.machineMove();
            } else if (this.levelComboBox.getSelectedItem() == "Expert") {
                this.maxDepthSetByUser = 6;
                this.machineMove();
            }

            this.levelComboBox.setEnabled(false);
            this.startGameButton.setEnabled(false);
        }
    }//GEN-LAST:event_startGameButtonActionPerformed

    private void humanPointsLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_humanPointsLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_humanPointsLabelActionPerformed

    private void machinePointsLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_machinePointsLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_machinePointsLabelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JTextField humanPointsLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox levelComboBox;
    private javax.swing.JTextField machinePointsLabel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JButton startGameButton;
    // End of variables declaration//GEN-END:variables
}
