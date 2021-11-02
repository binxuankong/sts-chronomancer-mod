package chronoMod.actions;

import chronoMod.patches.ChronoEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Iterator;

public class PredestinationAction extends AbstractGameAction {

    private AbstractPlayer p;

    public PredestinationAction(AbstractPlayer p, int effect) {
        this.p = p;
        this.amount = effect;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        for (int i = 0; i < this.amount; i++) {
            AbstractCard c = this.returnRandomXCostCard();
            if (this.p.hasPower("MasterRealityPower")) {
                c.upgrade();
            }
            c.freeToPlayOnce = true;
            // this.addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
            this.addToBot(new MakeTempCardInHandAction(c, true));
        }

        this.isDone = true;
    }

    private AbstractCard returnRandomXCostCard() {
        ArrayList<AbstractCard> list = new ArrayList();
        AbstractCard c;

        Iterator var1 = AbstractDungeon.srcUncommonCardPool.group.iterator();
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.hasTag(ChronoEnum.XCOST)) {
                // Uncommon card 2x more likely to appear
                list.add(c);
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }

        var1 = AbstractDungeon.srcRareCardPool.group.iterator();
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.hasTag(ChronoEnum.XCOST)) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }

        return (AbstractCard)list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }
}