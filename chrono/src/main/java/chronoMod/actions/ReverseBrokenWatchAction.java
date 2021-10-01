package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ReverseBrokenWatchAction extends AbstractGameAction {
    public ReverseBrokenWatchAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.setCostForTurn(c.cost);
        }
        
        this.isDone = true;
    }
}
