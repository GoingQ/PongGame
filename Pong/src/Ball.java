public class Ball {
    public Rect rect;
    public Rect leftPaddle, rightPaddle;
    public Text leftScoreText, rightScoreText;

    // Velocity x, y
    private double vx = -150.0;
    private double vy = 200.0;

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle, Text leftScoreText, Text rightScoreText) {
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.leftScoreText = leftScoreText;
        this.rightScoreText = rightScoreText;
    }

    public double calculateNewVelocityAngle(Rect paddle) {
        double relativeIntersectY = (paddle.y + (paddle.h / 2.0)) - (this.rect.y + (this.rect.h / 2.0));
        double normalIntersectY = relativeIntersectY / (paddle.h / 2.0);
        double theta = normalIntersectY * Constants.MAX_ANGLE;

        return Math.toRadians(theta);
    }

    public void update(double dt) {

        if (vx < 0) {
            if (this.rect.x <= this.leftPaddle.x + this.leftPaddle.w && this.rect.x + this.rect.w >= this.leftPaddle.x &&
                    this.rect.y >= this.leftPaddle.y && this.rect.y <= this.leftPaddle.y + this.leftPaddle.h) {
                double theta = calculateNewVelocityAngle(leftPaddle);
                double newVx = Math.abs(Math.cos(theta)) * Constants.BALL_SPEED;
                double newVy = (-Math.sin(theta)) * Constants.BALL_SPEED;

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;
            } else if (this.rect.x + this.rect.w < this.leftPaddle.x) {
                int rightScore = Integer.parseInt(rightScoreText.text);
                rightScore++;
                rightScoreText.text = "" + rightScore;
                this.rect.x = Constants.SCREEN_WIDTH / 2.0;
                this.rect.y = Constants.SCREEN_HEIGHT / 2.0;
                this.vx = -150.0;
                this.vy = 200.0;
                if (rightScore >= Constants.WIN_SCORE) {
                    Main.changeState(2);
                }
            }
        } else if (vx > 0) {
            if (this.rect.x + this.rect.w >= this.rightPaddle.x && this.rect.x <= this.rightPaddle.x + this.rightPaddle.w &&
                    this.rect.y >= this.rightPaddle.y && this.rect.y <= this.rightPaddle.y + this.rightPaddle.h) {
                double theta = calculateNewVelocityAngle(rightPaddle);
                double newVx = Math.abs(Math.cos(theta)) * Constants.BALL_SPEED;
                double newVy = (-Math.sin(theta)) * Constants.BALL_SPEED;

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;
            } else if (this.rect.x + this.rect.w > this.rightPaddle.x + this.rightPaddle.w) {
                int leftScore = Integer.parseInt(leftScoreText.text);
                leftScore++;
                leftScoreText.text = "" + leftScore;
                this.rect.x = Constants.SCREEN_WIDTH / 2.0;
                this.rect.y = Constants.SCREEN_HEIGHT / 2.0;
                this.vx = -150.0;
                this.vy = 200.0;
                if (leftScore >= Constants.WIN_SCORE) {
                    Main.changeState(2);
                }
            }
        }

        if (vy > 0) {
            if (this.rect.y + this.rect.h > Constants.SCREEN_HEIGHT) {
                this.vy *= -1;
            }
        } else if (vy < 0) {
            if (this.rect.y < Constants.TOOLBAR_HEIGHT) {
                this.vy *= -1;
            }
        }

        // position = position + velocity
        // velocity = velocity + acceleration
        this.rect.x += vx * dt;
        this.rect.y += vy * dt;

    }
}
