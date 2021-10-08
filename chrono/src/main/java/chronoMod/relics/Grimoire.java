package chronoMod.relics;

import basemod.abstracts.CustomRelic;
import chronoMod.ChronoMod;
import chronoMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class Grimoire extends CustomRelic {
    private static final String RELIC_ID = Grimoire.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(RELIC_ID);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(RELIC_ID + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(RELIC_ID + ".png"));

    public Grimoire() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void onTrigger() {
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        ArrayList<AbstractCard> groupCopy = new ArrayList();
        Iterator var4 = AbstractDungeon.player.hand.group.iterator();

        while (true) {
            while (var4.hasNext()) {
                AbstractCard c = (AbstractCard) var4.next();
                if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                    groupCopy.add(c);
                } else {
                    ChronoMod.logger.info("COST IS 0: " + c.name);
                }
            }

            var4 = AbstractDungeon.actionManager.cardQueue.iterator();

            while (var4.hasNext()) {
                CardQueueItem i = (CardQueueItem) var4.next();
                if (i.card != null) {
                    ChronoMod.logger.info("INVALID: " + i.card.name);
                    groupCopy.remove(i.card);
                }
            }

            AbstractCard c = null;
            if (groupCopy.isEmpty()) {
                ChronoMod.logger.info("NO VALID CARDS");
            } else {
                ChronoMod.logger.info("VALID CARDS: ");
                Iterator var9 = groupCopy.iterator();

                while (var9.hasNext()) {
                    AbstractCard cc = (AbstractCard) var9.next();
                    ChronoMod.logger.info(cc.name);
                }

                c = (AbstractCard) groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }

            if (c != null) {
                ChronoMod.logger.info("Mummified hand: " + c.name);
                c.setCostForTurn(0);
            } else {
                ChronoMod.logger.info("ERROR: MUMMIFIED HAND NOT WORKING");
            }
            break;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public CustomRelic makeCopy() {
        return new Grimoire();
    }
}
