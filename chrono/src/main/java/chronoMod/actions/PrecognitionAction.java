package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PrecognitionAction extends AbstractGameAction {

    private AbstractPlayer p;
    private boolean upgraded;

    public PrecognitionAction(AbstractPlayer p, boolean upgraded, int effect) {
        this.p = p;
        this.upgraded = upgraded;
        this.amount = effect;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        for (int i = 0; i < this.amount; i++) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
            if (this.upgraded) {
                c.upgrade();
            }

            c.setCostForTurn(0);
            this.addToBot(new MakeTempCardInHandAction(c, 1));
        }

        this.isDone = true;
    }
}