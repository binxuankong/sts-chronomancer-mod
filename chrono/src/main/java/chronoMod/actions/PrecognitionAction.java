package chronoMod.actions;

import chronoMod.patches.ChronoEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Iterator;

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
            // AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
            AbstractCard c = this.returnRandomXCostCard();
            if (this.upgraded) {
                c.upgrade();
            }
            c.freeToPlayOnce = true;
            // this.addToBot(new MakeTempCardInHandAction(c, 1));
            this.addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
        }

        this.isDone = true;
    }

    private AbstractCard returnRandomXCostCard() {

        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var1 = AbstractDungeon.srcCommonCardPool.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.hasTag(ChronoEnum.XCOST)) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }

        var1 = AbstractDungeon.srcUncommonCardPool.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.hasTag(ChronoEnum.XCOST)) {
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