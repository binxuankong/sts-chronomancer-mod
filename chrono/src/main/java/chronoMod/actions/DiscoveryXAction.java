package chronoMod.actions;

import chronoMod.patches.ChronoEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class DiscoveryXAction extends AbstractGameAction {

    private AbstractPlayer p;
    private boolean retrieveCard = false;

    public DiscoveryXAction(int amount) {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        ArrayList generatedCards = this.returnRandomXCostCard();

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    AbstractCard disCard2 = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (this.p.hasPower("MasterRealityPower")) {
                        disCard.upgrade();
                        disCard2.upgrade();
                    }
                    disCard.freeToPlayOnce = true;
                    disCard2.freeToPlayOnce = true;
                    disCard.current_x = -1000.0F * Settings.xScale;
                    disCard2.current_x = -1000.0F * Settings.xScale + AbstractCard.IMG_HEIGHT_S;
                    if (this.amount == 1) {
                        if (this.p.hand.size() < 10) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        }
                        disCard2 = null;
                    } else if (this.p.hand.size() + this.amount <= 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else if (this.p.hand.size() == 9) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> returnRandomXCostCard() {
        // Get all X cost cards
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

        // Select 3 out of all X cost cards
        ArrayList derp = new ArrayList();

        while(derp.size() != 3) {
            boolean dupe = false;
            AbstractCard tmp = (AbstractCard)list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));

            Iterator var5 = derp.iterator();

            while(var5.hasNext()) {
                AbstractCard existC = (AbstractCard)var5.next();
                if (existC.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                derp.add(tmp.makeCopy());
            }
        }

        return derp;
    }
}